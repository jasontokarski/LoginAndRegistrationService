package com.loginform.service;

import java.time.Clock;
import java.util.Date;
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

import de.mkammerer.argon2.Argon2;
import reactor.core.publisher.Mono;

import com.loginform.repository.*;
import com.loginform.util.Utils;

@Service("userService")
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Argon2 argon2;

    @Autowired
    private Clock clock;

    @Value("${jwt.hostname}")
    private String jwtHostName;

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByUserName(String userName) {
        Optional<UserEntity> result = userRepository.findByUserName(userName);
        return result;
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


    public Boolean validatePassword(String username, String password) {
        return argon2.verify(findByUserName(username).get().getPassword(), password.toCharArray());
    }

    @Override
    public String generateApiKey() {
        return Utils.generateRandomString(16, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz");
    }

    @Override
    public Mono<ResponseEntity<TokenEntity>> buildCredentialEntity(UserEntity user) {
        Optional<UserEntity> returnedUser = userRepository.findByUserName(user.getUserName());
        if(!returnedUser.isPresent()) {
            CredentialEntity credentialEntity = new CredentialEntity(user.getUserName(), null, generateApiKey());
            Utils.hashPass(user, argon2);
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
                    user.setCreationDate(new Date(clock.instant().toEpochMilli()));
                    userRepository.save(user);
                })
                .doOnError(error -> {
                    error.printStackTrace();
                });
        } else {
            return Mono.error(new RuntimeException("User does not exist!"));
        }
    }

    @Override
    public Mono<ResponseEntity<TokenEntity>> retrieveToken(String userName) {
        return WebClient.builder()
        .baseUrl(jwtHostName)
        .build()
        .post()
        .uri("/jwt/request")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(new CredentialEntity(userName, null, findByUserName(userName).get().getCredentialEntity().getApiKey())))
        .retrieve()
        .toEntity(TokenEntity.class)
        .doOnSuccess(response -> {
            response.getBody().getStatusEntity().setStatus("Login successful.");
        })
        .doOnError(error -> {
            error.printStackTrace();
        });
    }
}
