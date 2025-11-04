
const S = { requests: [], kb: [], page: { pending:1, resolved:1, size:10 }, query:'', status:'ALL' };
const $ = id => document.getElementById(id);
const fmt = t => t ? new Date(t).toLocaleString() : '';
const esc = s => (s||'').replace(/[&<>"]/g, c=>({ '&':'&amp;','<':'&lt;','>':'&gt;' }[c]));
const toast = m => { const t=$('toast'); t.textContent=m; t.style.display='block'; setTimeout(()=>t.style.display='none',1200); };

document.querySelectorAll('nav button').forEach(b=>b.addEventListener('click', ()=>showTab(b.dataset.tab)));
$('modalClose').onclick=()=>$('modal').style.display='none';
$('refresh').onclick=()=>loadAll();
$('seed').onclick=async ()=>{
  await fetch('/calls?question=What%20are%20your%20hours%3F&callerName=Avi', { method:'POST' });
  await loadRequests(); toast('Seeded a pending request');
};
$('search').oninput=e=>{ S.query=e.target.value.toLowerCase(); render(); };
$('status').onchange=e=>{ S.status=e.target.value; render(); };
$('kbSearch')?.addEventListener('input', renderKB);

function showTab(name){
  document.querySelectorAll('nav button').forEach(b=>b.classList.toggle('active', b.dataset.tab===name));
  document.querySelectorAll('.tab').forEach(s=>s.classList.remove('show'));
  $(name).classList.add('show');
  if(name==='learned') loadKB();
}

async function loadRequests(){ const r=await fetch('/requests'); S.requests=await r.json(); render(); }
async function loadKB(){ const r=await fetch('/knowledge'); S.kb=await r.json(); renderKB(); }
async function loadAll(){ await Promise.all([loadRequests(), loadKB()]); }

function filtered(list){
  let out = list;
  if(S.status!=='ALL') out = out.filter(x=>x.status===S.status);
  if(S.query) out = out.filter(x=> (x.question||'').toLowerCase().includes(S.query) || (x.callerName||'').toLowerCase().includes(S.query));
  return out;
}
function page(list, name){ const start=(S.page[name]-1)*S.page.size; const items=list.slice(start, start+S.page.size); const pages=Math.max(1, Math.ceil(list.length/S.page.size)); S.page[name]=Math.min(S.page[name], pages); return { items, pages }; }
function pager(elId, name, pages){ const c=$(elId); c.innerHTML=''; for(let i=1;i<=pages;i++){ const b=document.createElement('button'); b.textContent=i; if(i===S.page[name]) b.disabled=true; b.onclick=()=>{ S.page[name]=i; render(); }; c.appendChild(b);} }

function render(){
  const pend = filtered(S.requests.filter(x=>x.status==='PENDING'));
  const res  = filtered(S.requests.filter(x=>x.status!=='PENDING'));

  const p = page(pend,'pending');
  $('pendingTable').innerHTML = `
    <tr><th>ID</th><th>Question</th><th>Caller</th><th>Created</th><th>Status</th><th>Action</th></tr>
    ${p.items.map(r=>`
      <tr>
        <td>${r.id}</td>
        <td><a href="#" data-id="${r.id}" class="details">${esc(r.question||'')}</a></td>
        <td>${esc(r.callerName||'')}</td>
        <td>${fmt(r.createdAt)}</td>
        <td><span class="badge pending">PENDING</span></td>
        <td class="actions">
          <input type="text" id="ans${r.id}" placeholder="Answer"/>
          <button onclick="resolveReq(${r.id})">Resolve</button>
          <button onclick="unresolveReq(${r.id})">Unresolve</button>
        </td>
      </tr>`).join('')}
  `;
  pager('pendingPager','pending', p.pages);
  wireDetails('pendingTable');

  const rr = page(res,'resolved');
  $('resolvedTable').innerHTML = `
    <tr><th>ID</th><th>Question</th><th>Caller</th><th>Status</th><th>Resolved At</th></tr>
    ${rr.items.map(r=>`
      <tr>
        <td>${r.id}</td>
        <td>${esc(r.question||'')}</td>
        <td>${esc(r.callerName||'')}</td>
        <td><span class="badge ${r.status==='RESOLVED'?'resolved':'unresolved'}">${r.status}</span></td>
        <td>${fmt(r.resolvedAt)}</td>
      </tr>`).join('')}
  `;
  pager('resolvedPager','resolved', rr.pages);
}

function wireDetails(tableId){
  document.querySelectorAll(`#${tableId} a.details`).forEach(a=>{
    a.onclick=(e)=>{
      e.preventDefault();
      const id=a.dataset.id;
      const r=S.requests.find(x=>String(x.id)===String(id));
      $('modalTitle').textContent='Request Details';
      $('modalBody').innerHTML=`
        <p><b>ID</b>: ${r.id}</p>
        <p><b>Caller</b>: ${esc(r.callerName||'')}</p>
        <p><b>Question</b>: ${esc(r.question||'')}</p>
        <p><b>Status</b>: ${r.status}</p>
        <p><b>Created</b>: ${fmt(r.createdAt)}</p>
        <p><b>Resolved</b>: ${fmt(r.resolvedAt)||'-'}</p>
        <input id="modalAnswer" placeholder="Type answer"/>
        <div class="actions" style="margin-top:8px">
          <button onclick="resolveReq(${r.id}, true)">Submit</button>
          <button onclick="unresolveReq(${r.id})">Unresolve</button>
        </div>`;
      $('modal').style.display='block';
    };
  });
}

function renderKB(){
  const q = ($('kbSearch')?.value||'').toLowerCase();
  const list = S.kb.filter(k=> (k.question||'').toLowerCase().includes(q) || (k.answer||'').toLowerCase().includes(q));
  $('kbTable').innerHTML = `
    <tr><th>ID</th><th>Question</th><th>Answer</th><th>Updated</th></tr>
    ${list.map(k=>`
      <tr>
        <td>${k.id||''}</td>
        <td>${esc(k.question||'')}</td>
        <td>${esc(k.answer||'')}</td>
        <td>${fmt(k.updatedAt)||''}</td>
      </tr>`).join('')}
  `;
}

async function resolveReq(id, fromModal=false){
  const v = fromModal ? $('modalAnswer').value : ($('ans'+id)?.value||'');
  if(!v){ toast('Enter an answer'); return; }
  await fetch(`/supervisor/resolve?requestId=${id}&answer=${encodeURIComponent(v)}`, { method:'POST' });
  log(`Resolved #${id}: ${v}`); toast('Resolved and saved to KB');
  $('modal').style.display='none';
  await loadRequests(); await loadKB();
}

async function unresolveReq(id){
  await fetch(`/supervisor/unresolve?requestId=${id}`, { method:'POST' });
  log(`Marked #${id} UNRESOLVED`); toast('Marked UNRESOLVED');
  await loadRequests();
}

function log(msg){
  const li=document.createElement('li');
  li.textContent=`[${fmt(new Date())}] ${msg}`;
  $('log').prepend(li);
  while($('log').children.length>40) $('log').removeChild($('log').lastChild);
}

showTab('pending');
loadAll();
setInterval(loadRequests, 12000);
