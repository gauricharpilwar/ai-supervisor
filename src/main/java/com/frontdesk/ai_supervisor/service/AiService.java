package com.frontdesk.ai_supervisor.service;

import com.frontdesk.ai_supervisor.model.Helprequest;
import com.frontdesk.ai_supervisor.model.KnowledgebaseEntry;
import com.frontdesk.ai_supervisor.repository.HelpRequestRepository;
import com.frontdesk.ai_supervisor.repository.KnowledgeBaseRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AiService {

    private final HelpRequestRepository helpRequestRepository;
    private final KnowledgeBaseRepository knowledgeBaseRepository;

    public String handleCall(String question, String callerName) {
        KnowledgebaseEntry kbEntry = knowledgeBaseRepository.findByQuestion(question);
        if (kbEntry != null) {
            return "AI: " + kbEntry.getAnswer();
        }
        Helprequest request = new Helprequest();
        request.setQuestion(question);
        request.setCallerName(callerName);
        request.setStatus("PENDING");
        request.setCreatedAt(LocalDateTime.now());
        helpRequestRepository.save(request);
        return "AI: Let me check with my supervisor and get back to you.";
    }
}
