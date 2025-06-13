package com.example.backend;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final String SECRET_KEY = "your_very_long_and_secure_secret_key_that_is_at_least_32_characters_long_for_hs256";
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    private static final long EXPIRATION_TIME = 86400000; // 1 day

    @Autowired
    private AuthUserRepository authUserRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (authUserRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        AuthUser user = new AuthUser();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        authUserRepository.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<AuthUser> userOpt = authUserRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPasswordHash())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        String token = Jwts.builder()
                .setSubject(userOpt.get().getEmail())
                .claim("name", userOpt.get().getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Data
    public static class RegisterRequest {
        private String name;
        private String email;
        private String password;
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Data
    public static class JwtResponse {
        private final String token;
    }
} 