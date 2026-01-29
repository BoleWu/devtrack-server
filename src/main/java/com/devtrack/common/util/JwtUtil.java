/**
 * JWT 工具类，用于处理 JWT 令牌的生成、验证和解析
 */
package com.devtrack.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    // JWT 密钥，用于签名和验证 JWT 令牌
    @Value("${jwt.secret}")
    private String secret;
    // JWT 令牌的过期时间，单位为毫秒
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 从 JWT 令牌中提取用户名
     *
     * @param token JWT 令牌
     * @return 用户名
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 从 JWT 令牌中提取过期时间
     *
     * @param token JWT 令牌
     * @return 过期时间
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 从 JWT 令牌中提取指定类型的声明
     *
     * @param token          JWT 令牌
     * @param claimsResolver 声明解析器函数
     * @param <T>            声明类型
     * @return 解析出的声明值
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 解析 JWT 令牌并获取所有声明
     *
     * @param token JWT 令牌
     * @return 所有声明
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 判断 JWT 令牌是否已过期
     *
     * @param token JWT 令牌
     * @return 如果已过期则返回 true，否则返回 false
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 根据用户详细信息生成 JWT 令牌
     *
     * @param userDetails 用户详细信息
     * @return 生成的 JWT 令牌
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * 创建 JWT 令牌
     *
     * @param claims  声明集合
     * @param subject 主题（通常是用户名）
     * @return 生成的 JWT 令牌
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    /**
     * 验证 JWT 令牌是否有效
     *
     * @param token        JWT 令牌
     * @param userDetails  用户详细信息
     * @return 如果令牌有效则返回 true，否则返回 false
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 验证 JWT 令牌是否有效
     *
     * @param token   JWT 令牌
     * @param username 用户名
     * @return 如果令牌有效则返回 true，否则返回 false
     */
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (username.equals(extractedUsername) && !isTokenExpired(token));
    }

    /**
     * 获取签名密钥
     *
     * @return 签名密钥字节数组
     */
    private byte[] getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes).getEncoded();
    }
}
