package app.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.time.Instant;
import java.util.Date;

public class JwtUtil {
    //hardcoding secretkey and expiry time, should be more cautious in real apps
    private static final String SECRET = "SuperSecureSecretKey";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private static final long EXPIRATION_TIME = 86400000; // 1 day (in milliseconds)

    public static String generateToken(String username, String role) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(EXPIRATION_TIME / 1000))
                .sign(ALGORITHM);
    }

    public static DecodedJWT validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM)
                    .acceptLeeway(1)  // Allow 1 sec leeway
                    .build();

            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public static String getUsernameFromToken(String token) {
        DecodedJWT jwt = validateToken(token);
        return (jwt != null) ? jwt.getSubject() : null;
    }
}
