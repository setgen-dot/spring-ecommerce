package com.example.repository;

import com.example.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    boolean existsByEmail(String email);

    Optional<Credential> findByEmail(String email);
}
