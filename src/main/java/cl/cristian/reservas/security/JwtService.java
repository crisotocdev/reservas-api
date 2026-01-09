package cl.cristian.reservas.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // IMPORTANTE: en prod, esto va en application.properties (te lo dejo abajo)
    private static final String SECRET =
            "CAMBIA_ESTA_CLAVE_SUPER_SECRETA_DE_32+_CARACTERES_1234567890";
    private static final long EXP_MS = 1000L * 60 * 60; // 1 hora

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String subjectEmail) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + EXP_MS);

        return Jwts.builder()
                .setSubject(subjectEmail)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return parseClaims(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
