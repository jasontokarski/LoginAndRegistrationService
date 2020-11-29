package com.loginform.service;

import java.time.Clock;
import java.util.List;
import java.util.Optional;

import com.loginform.model.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.mkammerer.argon2.Argon2;

import com.loginform.repository.*;
import com.loginform.util.Utils;

@Service("userService")
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Clock clock;

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
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
    public void delete(Long Id) {
        userRepository.deleteById(Id);
    }

    @Override
    public Boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public String generateApiKey() {
        return Utils.generateRandomString(16, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz");
    }
}
