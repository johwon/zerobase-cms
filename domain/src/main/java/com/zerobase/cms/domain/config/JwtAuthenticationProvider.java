package com.zerobase.cms.domain.config;

import com.zerobase.cms.domain.domain.common.UserType;
import com.zerobase.cms.domain.domain.common.UserVo;
import com.zerobase.cms.domain.util.Aes256Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


public class JwtAuthenticationProvider {

    // 안전한 랜덤 키를 512비트로 생성 (64바이트)
    private final Key key;

    private long tokenValidTime = 1000L * 60 * 60 * 24; // 하루

    public JwtAuthenticationProvider(String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userPK, Long id, UserType userType){
        Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(userPK)).setId(id.toString());
        claims.put("roles", userType);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String jwtToken){
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public UserVo getUserVo(String token){
        try {
            Claims c = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("Claims ID: " + c.getId());
            System.out.println("Claims Subject (encrypted): " + c.getSubject());

            String email = Aes256Util.decrypt(c.getSubject());
            System.out.println("Decrypted email: " + email);

            Long userId = Long.valueOf(c.getId());
            return new UserVo(userId, email);
        } catch (Exception e) {
            System.out.println("Exception in getUserVo: " + e.getMessage());
            e.printStackTrace();
            return null;  // 또는 적절한 예외 처리
        }
    }



}