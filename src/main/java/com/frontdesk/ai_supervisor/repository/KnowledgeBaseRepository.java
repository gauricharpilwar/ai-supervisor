package com.frontdesk.ai_supervisor.repository;
import com.frontdesk.ai_supervisor.model.KnowledgebaseEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  KnowledgeBaseRepository extends JpaRepository <KnowledgebaseEntry, Long> {
    KnowledgebaseEntry findByQuestion(String question);
}
