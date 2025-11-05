package com.frontdesk.ai_supervisor.controller;

import com.frontdesk.ai_supervisor.model.Helprequest;
import com.frontdesk.ai_supervisor.model.KnowledgebaseEntry;
import com.frontdesk.ai_supervisor.repository.HelpRequestRepository;
import com.frontdesk.ai_supervisor.repository.KnowledgeBaseRepository;
import org.springframework.web.bind.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
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
    public String resolveRequest(@RequestBody ResolveRequest request) {
        Helprequest helpRequest = helpRequestRepository.findById(request.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found"));

        helpRequest.setStatus("RESOLVED");
        helpRequest.setResolvedAt(LocalDateTime.now());
        helpRequestRepository.save(helpRequest);

        KnowledgebaseEntry entry = new KnowledgebaseEntry();
        entry.setQuestion(helpRequest.getQuestion());
        entry.setAnswer(request.getAnswer());
        knowledgeBaseRepository.save(entry);

        return "Resolved";
    }

    @Data
    public static class ResolveRequest {
        private Long requestId;
        private String answer;
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


