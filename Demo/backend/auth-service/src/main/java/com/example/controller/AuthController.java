package com.example.controller;

import com.example.entities.User;
import com.example.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    private final String jwtSecret = "YWHx2ms0eGJhYlN6TkJqbENBblUyTktWbEZka3NaNElvNkliZlJ3czZ1skhqbdjqw";
    private final long expirationTime = 86400000; // 1 day in ms

    @PostMapping("/auth/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User loginUser) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(role -> role.replace("ROLE_", "")) // Remove ROLE_ prefix
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//                .signWith(key, Jwts.SIG.HS256) // Explicitly use HS256
                .signWith(key, SignatureAlgorithm.HS256) // <-- use SignatureAlgorithm
                .compact();

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}