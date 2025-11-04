package com.frontdesk.ai_supervisor.controller;


import com.frontdesk.ai_supervisor.model.Helprequest;
import com.frontdesk.ai_supervisor.repository.HelpRequestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui")
public class SupervisorUIController {

    private final HelpRequestRepository helpRequestRepository;

    public SupervisorUIController(HelpRequestRepository helpRequestRepository) {
        this.helpRequestRepository = helpRequestRepository;
    }

    @GetMapping("/pending")
    public String getPendingRequests(Model model) {
        List<Helprequest> pendingRequests = helpRequestRepository.findAll()
                .stream()
                .filter(r -> r.getStatus().equals("PENDING"))
                .toList();
        model.addAttribute("requests", pendingRequests);
        return "pending";
    }

    @PostMapping("/resolve")
    public String resolveRequest(
            @RequestParam Long requestId,
            @RequestParam String answer) {

        return "redirect:/ui/pending";
    }

    @GetMapping("/resolved")
    public String getResolvedRequests(Model model) {
        List<Helprequest> resolved = helpRequestRepository.findAll()
                .stream()
                .filter(r -> r.getStatus().equals("RESOLVED"))
                .toList();
        model.addAttribute("requests", resolved);
        return "resolved";
    }
}

