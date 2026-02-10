package com.devtrack;


import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;

public class Test {
    @org.junit.jupiter.api.Test
    public void test() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        String base64Key = java.util.Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("JWT_SECRET=" + base64Key);

//        System.out.println(LocalDateTime);
    }
}
