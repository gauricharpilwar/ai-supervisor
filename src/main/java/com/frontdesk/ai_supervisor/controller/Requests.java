package com.frontdesk.ai_supervisor.controller;

import com.frontdesk.ai_supervisor.model.Helprequest;
import com.frontdesk.ai_supervisor.repository.HelpRequestRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class Requests {
        private final  HelpRequestRepository helpRequestRepository;

        public Requests(HelpRequestRepository helpRequestRepository){
            this.helpRequestRepository = helpRequestRepository;
        }

        @GetMapping
                public List<Helprequest> getAllRequests() {
            return helpRequestRepository.findAll();
        }
    }


