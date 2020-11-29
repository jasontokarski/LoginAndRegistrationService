package com.loginform.service;

import com.loginform.model.UserEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import java.time.Clock;
import java.util.Optional;

public interface JwtService {
    public boolean isAuthorized(UserEntity userEntity);
    public boolean isValidToken(String jwt);
    public String createToken(Authentication authentication);
    public Claims decodeToken(String jwt);
    public void setClock(Clock clock);
    public String getUsernameFromJwt(String token);
}
