package com.example.backend;

import com.example.backend.dto.UserRequest;
import com.example.backend.dto.UserResponse;
import com.example.backend.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
            .map(userMapper::toResponse)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(u -> ResponseEntity.ok(userMapper.toResponse(u)))
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        User user = userMapper.toEntity(request);
        // Find the highest existing ID and increment it
        Long maxId = userRepository.findAll().stream()
            .mapToLong(User::getId)
            .max()
            .orElse(0L);
        user.setId(maxId + 1);
        
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(userMapper.toResponse(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        User user = userMapper.toEntity(request);
        user.setId(id);
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(userMapper.toResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 