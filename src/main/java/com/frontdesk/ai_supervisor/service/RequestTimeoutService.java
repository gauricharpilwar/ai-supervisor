package com.frontdesk.ai_supervisor.service;

import com.frontdesk.ai_supervisor.model.Helprequest;
import com.frontdesk.ai_supervisor.repository.HelpRequestRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestTimeoutService {
    private final HelpRequestRepository helpRequestRepository;

    public RequestTimeoutService(HelpRequestRepository helpRequestRepository) {
        this.helpRequestRepository = helpRequestRepository;
    }

    @Scheduled(fixedRate = 300000)
    public void checkForTimeouts() {
        List<Helprequest> pendingRequests = helpRequestRepository.findAll()
                .stream()
                .filter(r -> r.getStatus().equals("PENDING"))
                .toList();

        for (Helprequest request : pendingRequests) {
            if (request.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(10))) {
                request.setStatus("UNRESOLVED");
                helpRequestRepository.save(request);
                System.out.println("Request " + request.getId() + " has timed out and is marked as UNRESOLVED.");
            }
        }
    }
}