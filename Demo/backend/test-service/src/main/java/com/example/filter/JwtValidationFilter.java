package com.example.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtValidationFilter.class);
    private final String jwtSecret = "YWHx2ms0eGJhYlN6TkJqbENBblUyTktWbEZka3NaNElvNkliZlJ3czZ1skhqbdjqw";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                @SuppressWarnings("unchecked")
                List<String> roles = claims.get("roles", List.class);
                logger.info("Validated token for user: {}, roles: {}", claims.getSubject(), roles);
                request.setAttribute("roles", roles); // Pass roles to controllers if needed
            } catch (Exception e) {
                logger.error("Invalid token in Test Service: {}", e.getMessage());
                // Do not set 401; let API Gateway handle authentication
            }
        } else {
            logger.warn("No Bearer token found in request");
            // Do not set 401; let API Gateway handle authentication
        }
        filterChain.doFilter(request, response); // Always proceed
    }
}