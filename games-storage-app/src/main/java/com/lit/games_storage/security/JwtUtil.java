package com.lit.games_storage.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "mdrkjp13274047!#@&$)$&";
    private static final String REFRESH_SECRET_KEY = "thunder";
    private static final long ACCESS_TOKEN_EXPIRATION = 900000; // 15 minutos
    private static final long REFRESH_TOKEN_EXPIRATION = 604800000; // 7 dias

    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    private final Algorithm refreshAlgorithm = Algorithm.HMAC256(REFRESH_SECRET_KEY);

    public String generateAccessToken(String username){
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .sign(algorithm);
    }

    public String generateRefreshToken(String username){
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .sign(algorithm);
    }

    public String extractUsername(String token) {
        return decodeToken(token, algorithm).getSubject();
    }

    public boolean validateAccessToken(String token, String username){
        return validateToken(token, username, algorithm);
    }

    public boolean refreshValidateToken(String token, String username){
        return validateToken(token, username, refreshAlgorithm);
    }

    private boolean validateToken(String token, String username, Algorithm algorithm){
        try {
            JWTVerifier verifier = JWT.require(algorithm).withSubject(username).build();
            verifier.verify(token);
            return true;
        }
        catch (JWTVerificationException e){
            return false;
        }
    }

    private DecodedJWT decodeToken(String token, Algorithm algorithm){
        return JWT.require(algorithm).build().verify(token);
    }
}
