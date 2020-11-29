package com.loginform.controller;

import com.loginform.dto.AuthenticationResponse;
import com.loginform.dto.LoginRequest;
import com.loginform.dto.RegistrationRequest;
import com.loginform.exception.AppException;
import com.loginform.dto.ApiResponse;
import com.loginform.model.RoleEntity;
import com.loginform.model.RoleName;
import com.loginform.model.UserEntity;
import com.loginform.repository.RoleRepository;
import com.loginform.service.JwtService;
import com.loginform.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Clock;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class Controller {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Clock clock;

    @PostMapping(value = "/login", headers = "Accept=application/json")
    public ResponseEntity<?> validateLogin(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.createToken(authentication);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping(value = "/register", headers = "Accept=application/json")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        if (userService.usernameExists(registrationRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Username already exists."));
        }
        if(userService.emailExists(registrationRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Email already exists."));
        }

        UserEntity userEntity = new UserEntity(registrationRequest.getUsername(),
                passwordEncoder.encode(registrationRequest.getPassword()),
                registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                registrationRequest.getEmail(),
                new Date(clock.instant().toEpochMilli()));

        RoleEntity roleEntity = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        Set<RoleEntity> roleEntitySet = new HashSet<>();
        roleEntitySet.add(roleEntity);
        userEntity.setRoleEntities(roleEntitySet);

        userService.save(userEntity);
        return ResponseEntity.ok(new ApiResponse(true, "Registration successful."));
    }
    
}
