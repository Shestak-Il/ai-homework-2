package com.example.backend.controller;

import com.example.backend.User;
import com.example.backend.UserController;
import com.example.backend.UserRepository;
import com.example.backend.dto.UserRequest;
import com.example.backend.dto.UserResponse;
import com.example.backend.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private UserResponse userResponse;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        testUser = createTestUser(1L, "test@example.com", "Test User");
        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setEmail("test@example.com");
        userResponse.setName("Test User");
        userResponse.setUsername("test@example.com");
        
        userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setName("Test User");
        userRequest.setUsername("test@example.com");
    }

   @Test
void getAllUsers_ShouldReturnListOfUsers() {
    // Arrange
    User user1 = createTestUser(1L, "user1@example.com", "User 1");
    User user2 = createTestUser(2L, "user2@example.com", "User 2");
    List<User> users = Arrays.asList(user1, user2);
    
    when(userRepository.findAll()).thenReturn(users);
    
    UserResponse response1 = new UserResponse();
    response1.setId(1L);
    response1.setEmail("user1@example.com");
    response1.setName("User 1");
    
    UserResponse response2 = new UserResponse();
    response2.setId(2L);
    response2.setEmail("user2@example.com");
    response2.setName("User 2");
    
    when(userMapper.toResponse(user1)).thenReturn(response1);
    when(userMapper.toResponse(user2)).thenReturn(response2);

    // Act
    List<UserResponse> response = userController.getAllUsers();

    // Assert
    assertNotNull(response);
    assertEquals(2, response.size());
    assertEquals("user1@example.com", response.get(0).getEmail());
    assertEquals("User 1", response.get(0).getName());
    assertEquals("user2@example.com", response.get(1).getEmail());
    assertEquals("User 2", response.get(1).getName());
    verify(userRepository, times(1)).findAll();
    verify(userMapper, times(1)).toResponse(user1);
    verify(userMapper, times(1)).toResponse(user2);
}

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userMapper.toResponse(testUser)).thenReturn(userResponse);

        // Act
        ResponseEntity<UserResponse> response = userController.getUserById(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(userResponse.getEmail(), response.getBody().getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).toResponse(testUser);
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<UserResponse> response = userController.getUserById(1L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, never()).toResponse(any(User.class));
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        // Arrange
        List<User> existingUsers = Arrays.asList(createTestUser(1L, "existing@example.com", "Existing User"));
        when(userRepository.findAll()).thenReturn(existingUsers);
        when(userMapper.toEntity(any(UserRequest.class))).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toResponse(testUser)).thenReturn(userResponse);

        // Act
        ResponseEntity<UserResponse> response = userController.createUser(userRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(userResponse.getEmail(), response.getBody().getEmail());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).toEntity(userRequest);
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toResponse(testUser);
    }

    @Test
    void updateUser_WhenUserExists_ShouldReturnUpdatedUser() {
        // Arrange
        UserRequest updateRequest = new UserRequest();
        updateRequest.setEmail("updated@example.com");
        updateRequest.setName("Updated User");
        updateRequest.setUsername("updated@example.com");
        
        UserResponse updatedResponse = new UserResponse();
        updatedResponse.setId(1L);
        updatedResponse.setEmail("updated@example.com");
        updatedResponse.setName("Updated User");
        updatedResponse.setUsername("updated@example.com");
        
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userMapper.toEntity(updateRequest)).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toResponse(testUser)).thenReturn(updatedResponse);

        // Act
        ResponseEntity<UserResponse> response = userController.updateUser(1L, updateRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(updatedResponse.getEmail(), response.getBody().getEmail());
        verify(userRepository, times(1)).existsById(1L);
        verify(userMapper, times(1)).toEntity(updateRequest);
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toResponse(testUser);
    }

    @Test
    void updateUser_WhenUserDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        UserRequest updateRequest = new UserRequest();
        updateRequest.setEmail("updated@example.com");
        updateRequest.setName("Updated User");
        updateRequest.setUsername("updated@example.com");
        
        when(userRepository.existsById(1L)).thenReturn(false);

        // Act
        ResponseEntity<UserResponse> response = userController.updateUser(1L, updateRequest);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userRepository, times(1)).existsById(1L);
        verify(userMapper, never()).toEntity(any());
        verify(userRepository, never()).save(any(User.class));
        verify(userMapper, never()).toResponse(any(User.class));
    }

    @Test
    void deleteUser_WhenUserExists_ShouldReturnNoContent() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = userController.deleteUser(1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_WhenUserDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = userController.deleteUser(1L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, never()).deleteById(any());
    }

    private User createTestUser(Long id, String email, String name) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setName(name);
        user.setUsername(email);
        return user;
    }
} 