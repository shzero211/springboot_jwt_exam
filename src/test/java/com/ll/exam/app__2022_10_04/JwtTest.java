package com.ll.exam.app__2022_10_04;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Base64;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest
public class JwtTest {
    @Value("${custom.jwt.secretKey}")
    private String secretKeyPlain;

    @Test
    @DisplayName("secretKey 가 존재해야한다")
    void t1(){
        System.out.println(secretKeyPlain);
        Assertions.assertNotNull(secretKeyPlain);
    }
    @Test
    @DisplayName("secretKey 원문으로 hmac 암호화 알고리즘에 맞느 SecretKey 객체를 만들수 있다.")
    void t2(){
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
        System.out.println(secretKey);
        assertThat(secretKey).isNotNull();
    }
}
