package com.loginform.service;

import java.util.List;
import java.util.Optional;

import com.loginform.model.TokenEntity;
import com.loginform.model.UserEntity;

import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Mono;

public interface UserService {
    List <UserEntity> findAll();

    void save(UserEntity user);

    Optional <UserEntity> findById(Long id);

    Optional <UserEntity> findByUserName(String userName);

    Optional <UserEntity> findByEmail(String email);

    void delete(long id);

    Boolean validateUserNameExists(String userName); 

    Boolean validateEmailExists(String email); 
    
    Boolean validatePassword(String username, String password);

    String generateApiKey();

    Mono<ResponseEntity<TokenEntity>> buildCredentialEntity(UserEntity user);
}
