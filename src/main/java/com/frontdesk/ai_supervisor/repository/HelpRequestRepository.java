package com.frontdesk.ai_supervisor.repository;
import com.frontdesk.ai_supervisor.model.Helprequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelpRequestRepository extends JpaRepository<Helprequest , Long> {
}
