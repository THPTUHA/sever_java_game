package com.example.demo.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.model.CustomUserDetails;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
    private final String JWT_SECRET = "fuckJava";
    private final long JWT_EXPIRATION = 604800000L;
    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                   .setSubject(Long.toString(userDetails.getUser().getId()))
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                   .compact();
    }

    public int getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();

        return Integer.parseInt(claims.getSubject());
    }
    public boolean validateToken(String authToken, HttpServletResponse response) throws IOException {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
            response.setStatus(403);
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
            // response.setStatus(403);
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
            response.setStatus(403);
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
            response.setStatus(403);
        }
        return false;
    }
}
