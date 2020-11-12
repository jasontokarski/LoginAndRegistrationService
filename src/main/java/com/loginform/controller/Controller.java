package com.loginform.controller;

import com.loginform.model.LoginEntity;
import com.loginform.model.StatusEntity;
import com.loginform.model.TokenEntity;
import com.loginform.model.UserEntity;
import com.loginform.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class Controller {

    @Autowired
    private UserService userService;

    StatusEntity statusEntity;

    TokenEntity tokenEntity;

    @PostMapping(value = "/login", headers = "Accept=application/json")
    public Mono<?> validateLogin(@RequestBody LoginEntity login) {
        if (!userService.validateUserNameExists(login.getUserName())) {
           return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON));
        }
        if (userService.validatePassword(login.getUserName(), login.getPassword())) {
            Mono<ResponseEntity<TokenEntity>> credential = userService.retrieveToken(login.getUserName());
            return credential;
        }
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON));
    }

    @PostMapping(value = "/register", headers = "Accept=application/json")
    public Mono<?> registerUser(@RequestBody UserEntity user) {
        if (userService.validateUserNameExists(user.getUserName()) || userService.validateEmailExists(user.getEmail())) {
            statusEntity = new StatusEntity("Username or email already exists.", 0, 0);
            return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(statusEntity));
        } else {
            Mono<ResponseEntity<TokenEntity>> credential = userService.buildCredentialEntity(user);
            return credential;
        }
    }
    
}
