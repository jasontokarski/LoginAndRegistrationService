package com.loginform.repository;

import java.util.Optional;

import com.loginform.model.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByUsername(String username);
	Optional<UserEntity> findByEmail(String email);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
    
}
