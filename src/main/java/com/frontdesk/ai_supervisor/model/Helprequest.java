package com.frontdesk.ai_supervisor.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Helprequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private String callerName;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

}


