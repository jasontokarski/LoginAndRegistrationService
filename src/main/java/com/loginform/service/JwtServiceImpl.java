package com.loginform.service;

import com.loginform.model.UserEntity;
import com.loginform.security.UserPrincipal;
import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Clock;
import java.util.Date;
import java.util.Optional;

@Service("jwtService")
public class JwtServiceImpl implements JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

    @Autowired
    private Clock clock;

    @Autowired
    UserPrincipal userPrincipal;

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Value("${jwt.issuer.key}")
    private String jwtIssuerKey;

    @Value("${jwt.ttl}")
    private Long ttl;

    public String createToken(Authentication authentication) {
        userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date(clock.instant().toEpochMilli());
        Date expiryDate = new Date(clock.instant().toEpochMilli() + ttl);
        byte[] apiKeySecretBytes = Base64.decodeBase64(jwtSecretKey);
        SecretKey signingKey = new SecretKeySpec(apiKeySecretBytes, "HS256");

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setIssuer(jwtIssuerKey)
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .setExpiration(expiryDate)
                .compact();
    }

    public Claims decodeToken(String jwt) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException {
        Claims claims = Jwts.parser()
                .setSigningKey(Base64.decodeBase64(jwtSecretKey))
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

    @Override
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    @Override
    public boolean isAuthorized(UserEntity userEntity) {
        return false;
    }

    public boolean isValidToken(String jwt) {
        try {
            decodeToken(jwt);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return false;
        }
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
