package com.loginform.repository;

import java.util.Optional;

import com.loginform.model.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByUserName(String userName);
	Optional<UserEntity> findByEmail(String email);
    
}
