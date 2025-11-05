package com.frontdesk.ai_supervisor.controller;

import com.frontdesk.ai_supervisor.service.AiService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
public class Calls {
    private final AiService aiService;

    public Calls(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/calls")
    public String simulateCall(@RequestBody CallRequest request) {
        return aiService.handleCall(request.getQuestion(), request.getCallerName());
    }

    @Data
    public static class CallRequest {
        private String callerName;
        private String question;
    }
}
