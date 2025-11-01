package com.example.config;

import com.example.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class JwtUtil {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    public UserDto getUserDto(final String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            UserDto userDto = new UserDto();
            userDto.setId(claims.get("userId", Long.class));
            userDto.setUsername(claims.getSubject());
            userDto.setRoles(Arrays.asList(claims.get("roles", String.class).split(",")));
            return userDto;
        } catch (Exception e) {
            // log the exception
        }
        return null;
    }

    public void validateToken(final String token) throws Exception {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
    }
}
