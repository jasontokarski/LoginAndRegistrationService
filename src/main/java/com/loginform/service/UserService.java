package com.loginform.service;

import java.util.List;
import java.util.Optional;
import com.loginform.model.UserEntity;

public interface UserService {
    List<UserEntity> findAll();

    void save(UserEntity user);

    Optional<UserEntity> findById(Long Id);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    void delete(Long Id);

    Boolean usernameExists(String username);

    Boolean emailExists(String email);

    String generateApiKey();
}
