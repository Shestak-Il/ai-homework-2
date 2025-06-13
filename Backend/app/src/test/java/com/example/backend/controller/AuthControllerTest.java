package com.example.backend.controller;

import com.example.backend.AuthController;
import com.example.backend.AuthUser;
import com.example.backend.AuthUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthUserRepository authUserRepository;

    @InjectMocks
    private AuthController authController;

    private AuthUser testUser;
    private AuthController.LoginRequest loginRequest;
    private AuthController.RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        testUser = new AuthUser();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setName("Test User");
        testUser.setPasswordHash(new BCryptPasswordEncoder().encode("password"));

        loginRequest = new AuthController.LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        registerRequest = new AuthController.RegisterRequest();
        registerRequest.setEmail("new@example.com");
        registerRequest.setName("New User");
        registerRequest.setPassword("password");
    }

    @Test
    void login_WithValidCredentials_ShouldReturnToken() {
        // Arrange
        when(authUserRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(testUser));

        // Act
        ResponseEntity<?> response = authController.login(loginRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof AuthController.JwtResponse);
        AuthController.JwtResponse jwtResponse = (AuthController.JwtResponse) response.getBody();
        assertNotNull(jwtResponse.getToken());
        verify(authUserRepository, times(1)).findByEmail(loginRequest.getEmail());
    }

    @Test
    void login_WithInvalidCredentials_ShouldReturnUnauthorized() {
        // Arrange
        when(authUserRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = authController.login(loginRequest);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
        verify(authUserRepository, times(1)).findByEmail(loginRequest.getEmail());
    }

    @Test
    void register_WithNewEmail_ShouldReturnSuccess() {
        // Arrange
        when(authUserRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(authUserRepository.save(any(AuthUser.class))).thenReturn(testUser);

        // Act
        ResponseEntity<?> response = authController.register(registerRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered", response.getBody());
        verify(authUserRepository, times(1)).findByEmail(registerRequest.getEmail());
        verify(authUserRepository, times(1)).save(any(AuthUser.class));
    }

    @Test
    void register_WithExistingEmail_ShouldReturnBadRequest() {
        // Arrange
        when(authUserRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(testUser));

        // Act
        ResponseEntity<?> response = authController.register(registerRequest);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Email already in use", response.getBody());
        verify(authUserRepository, times(1)).findByEmail(registerRequest.getEmail());
  

        verify(authUserRepository, never()).save(any(AuthUser.class));
    }
} 