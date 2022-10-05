package com.ll.exam.app__2022_10_04;


import com.ll.exam.app__2022_10_04.app.jwt.JwtProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest
public class JwtTest {
    @Value("${custom.jwt.secretKey}")
    private String secretKeyPlain;

    @Autowired
    private JwtProvider jwtProvider;
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
    @Test
    @DisplayName("JwtProvider 객체로 시크릿키 객체를 생성할수 있다.")
    void t3(){
        SecretKey secretKey= TestUtil.callMethod(jwtProvider,"getSecretKey");
        assertThat(secretKey).isNotNull();
    }
    @Test
    @DisplayName("JwtProvider 객체로 시크릿키 객체를 생성할수 있다.")
    void t4(){
        // 회원번호가 1이고
        // username이 admin 이고
        // ADMIN 역할과 MEMBER 역할을 동시에 가지고 있는 회원정보를 구성
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1L);
        claims.put("username", "admin");
        claims.put("authorities", Arrays.asList(
                new SimpleGrantedAuthority("ADMIN"),
                new SimpleGrantedAuthority("MEMBER"))
        );

        // 지금으로부터 5시간의 유효기간을 가지는 토큰을 생성
       String accessToken=jwtProvider.generateAccessToken(claims,60*60*5);
        System.out.println("accessToken : "+accessToken);
        assertThat(accessToken).isNotNull();
    }
    @Test
    @DisplayName("accessToken을 통해서 claims 를 얻을수 있다")
    void t5(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1L);
        claims.put("username", "admin");
        claims.put("authorities", Arrays.asList(
                new SimpleGrantedAuthority("ADMIN"),
                new SimpleGrantedAuthority("MEMBER"))
        );
        String accessToken=jwtProvider.generateAccessToken(claims,60*60*5);
        System.out.println("accessToken :" +accessToken);
        assertThat(jwtProvider.verify(accessToken)).isTrue();
        Map<String,Object> claimsFromToken=jwtProvider.getClaims(accessToken);
        System.out.println("claimsFromToken :"+claimsFromToken);
    }

}
