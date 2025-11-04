package com.frontdesk.ai_supervisor.controller;

import com.frontdesk.ai_supervisor.model.Helprequest;
import com.frontdesk.ai_supervisor.model.KnowledgebaseEntry;
import com.frontdesk.ai_supervisor.repository.HelpRequestRepository;
import com.frontdesk.ai_supervisor.repository.KnowledgeBaseRepository;
import org.springframework.web.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
// SupervisorController.java (additions)
@RestController
@RequestMapping("/supervisor")
public class Supervisor {
    private final HelpRequestRepository helpRequestRepository;
    private final KnowledgeBaseRepository knowledgeBaseRepository;

    public Supervisor(HelpRequestRepository helpRequestRepository, KnowledgeBaseRepository knowledgeBaseRepository) {
        this.helpRequestRepository = helpRequestRepository;
        this.knowledgeBaseRepository = knowledgeBaseRepository;
    }

    @PostMapping("/resolve")
    public String resolveRequest(@RequestParam Long requestId, @RequestParam String answer) {
        Helprequest request = helpRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus("RESOLVED");
        request.setResolvedAt(LocalDateTime.now());
        helpRequestRepository.save(request);

        KnowledgebaseEntry entry = new KnowledgebaseEntry();
        entry.setQuestion(request.getQuestion());
        entry.setAnswer(answer);
        knowledgeBaseRepository.save(entry);

        return "Resolved";
    }

    @PostMapping("/unresolve")
    public String unresolve(@RequestParam Long requestId) {
        Helprequest request = helpRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus("UNRESOLVED");
        helpRequestRepository.save(request);
        return "Unresolved";
    }
}

// KnowledgeController.java (new)

