package com.atom.application.repos;

import java.util.Optional;

import com.atom.application.models.PasswordResetRequest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetRequestRepository extends JpaRepository<PasswordResetRequest, Long> {

    public Optional<PasswordResetRequest> findByUserId(Long userId);

    public Optional<PasswordResetRequest> findByToken(String token);

}
