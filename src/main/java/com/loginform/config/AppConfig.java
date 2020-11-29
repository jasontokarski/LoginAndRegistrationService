package com.loginform.config;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Configuration
public class AppConfig {
    @Bean
    public Clock clock() { 
        return Clock.systemDefaultZone();
    } 
}
