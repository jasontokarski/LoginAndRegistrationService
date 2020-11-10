package com.loginform.config;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Configuration
public class AppConfig {

    @Bean
    public Argon2 argon2() {
        return Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id,
            32, 64);
    }

    @Bean
    public Clock clock() { 
        return Clock.systemDefaultZone();
    } 
}
