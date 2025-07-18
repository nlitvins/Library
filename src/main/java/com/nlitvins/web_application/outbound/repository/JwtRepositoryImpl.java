package com.nlitvins.web_application.outbound.repository;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.repository.JwtRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtRepositoryImpl implements JwtRepository {

    private final Key secretKey = Jwts.SIG.HS256.key().build();
    private final JwtParser jwtParser = Jwts.parser().verifyWith((SecretKey) secretKey).build();

    @Override
    public String getToken(User user) {
        Instant currentDateTime = Instant.now();
        return Jwts.builder()
                .subject(user.getUserName())
                .claim("userId", user.getId())
                .claim("role", String.format("ROLE_%s", user.getRole()))
                .claim("name", user.getName())
                .claim("secondName", user.getSecondName())
                .claim("email", user.getEmail())
                .issuedAt(Date.from(currentDateTime))
                .expiration(Date.from(currentDateTime.plus(25L, ChronoUnit.MINUTES)))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String getUserName(String token) {
        return jwtParser.parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public String getRole(String token) {
        return jwtParser.parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    @Override
    public Integer getUserId(String token) {
        return jwtParser.parseSignedClaims(token)
                .getPayload()
                .get("userId", Integer.class);
    }

    @Override
    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claims = jwtParser.parseSignedClaims(token);
            return Date.from(Instant.now()).before(claims.getPayload().getExpiration());
        } catch (JwtException e) {
            return false;
        }
    }
}
