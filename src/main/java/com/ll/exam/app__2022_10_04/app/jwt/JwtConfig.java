package com.ll.exam.app__2022_10_04.app.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
public class JwtConfig {
    @Value("${custom.jwt.secretKey}")
    private String secretKeyPlain;

    @Bean
    public  SecretKey jwtSecretKey(){
        String keyBase65Encoded= Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        SecretKey secretKey= Keys.hmacShaKeyFor(keyBase65Encoded.getBytes());
        return secretKey;
    }
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
