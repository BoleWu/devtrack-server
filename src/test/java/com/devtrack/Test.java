package com.devtrack;


import com.devtrack.common.util.ApiScanner;
import io.jsonwebtoken.Jwts;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

import javax.crypto.SecretKey;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.lang.reflect.Method;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;

public class Test {
    @org.junit.jupiter.api.Test
    public void test() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        String base64Key = java.util.Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("JWT_SECRET=" + base64Key);

//        System.out.println(LocalDateTime);
    }

}
