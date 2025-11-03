package com.frontdesk.ai_supervisor.controller;

import com.frontdesk.ai_supervisor.service.AiService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/calls")
public class Calls {
    private  final  AiService aiService;

    public  Calls(AiService aiService){
        this.aiService = aiService;
    }

    @PostMapping
    public String simulateCall(@RequestParam String question, @RequestParam String callerName ){
        return aiService.handleCall(question, callerName);

    }
}
