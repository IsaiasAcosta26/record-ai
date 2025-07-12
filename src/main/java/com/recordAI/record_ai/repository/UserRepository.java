package com.recordAI.record_ai.repository;


import com.recordAI.record_ai.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
