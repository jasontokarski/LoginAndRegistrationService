package com.loginform.repository;

import java.util.Optional;

import com.loginform.model.CredentialEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("credentialRepository")
public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {

	Optional<CredentialEntity> findByUserName(String userName);
    
}
