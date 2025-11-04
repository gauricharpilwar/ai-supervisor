package com.frontdesk.ai_supervisor.controller;
import org.springframework.web.bind.annotation.*;
import com.frontdesk.ai_supervisor.repository.KnowledgeBaseRepository;
import com.frontdesk.ai_supervisor.model.KnowledgebaseEntry;
import java.util.List;



@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {
    private final KnowledgeBaseRepository repo;
    public KnowledgeController(KnowledgeBaseRepository repo){ this.repo = repo; }

    @GetMapping
    public List<KnowledgebaseEntry> all(){ return repo.findAll(); }
}