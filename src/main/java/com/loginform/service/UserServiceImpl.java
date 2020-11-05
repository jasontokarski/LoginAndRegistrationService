package com.loginform.service;

import java.util.List;
import java.util.Optional;

import com.loginform.model.CredentialEntity;
import com.loginform.model.TokenEntity;
import com.loginform.model.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import com.loginform.repository.*;
import com.loginform.util.Utils;

@Service("userService")
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.hostname}")
    private String jwtHostName;

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public Boolean validateUserNameExists(String userName) {
        return findByUserName(userName).isPresent();
    }

    public Boolean validateEmailExists(String email) {
        return findByEmail(email).isPresent();
    }


    public Boolean validatePassword(UserEntity user) {
        return findByUserName(user.getUserName()).get().getPassword().equals(user.getPassword());
    }

    @Override
    public String generateApiKey() {
        return Utils.generateRandomString(16, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz");
    }

    @Override
    public Mono<ResponseEntity<TokenEntity>> buildCredentialEntity(UserEntity user) {
        Optional<UserEntity> returnedUser = userRepository.findByUserName(user.getUserName());
        CredentialEntity credentialEntity = new CredentialEntity(user.getUserName(), null, generateApiKey());
        if(!returnedUser.isPresent()) {
            return WebClient.builder()
                .baseUrl(jwtHostName)
                .build()
                .post()
                .uri("/jwt/add-user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(credentialEntity))
                .retrieve()
                .toEntity(TokenEntity.class)
                .doOnSuccess(response -> {
                    credentialEntity.setToken(response.getBody().getToken());
                    credentialEntity.setUserEntity(user);
                    user.setCredentialEntity(credentialEntity);
                    userRepository.save(user);
                })
                .doOnError(error -> {
                    error.printStackTrace();
                });
        } else {
            return Mono.error(new RuntimeException("User does not exist!"));
        }
    }
}
