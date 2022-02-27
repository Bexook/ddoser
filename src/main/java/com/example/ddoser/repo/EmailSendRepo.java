package com.example.ddoser.repo;

import com.example.ddoser.domain.EmailsSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailSendRepo extends JpaRepository<EmailsSend, Long> {
}
