package com.example.backend.mapper;

import com.example.backend.User;
import com.example.backend.dto.UserRequest;
import com.example.backend.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public User toEntity(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setWebsite(request.getWebsite());
        
        if (request.getAddress() != null) {
            user.setAddress(AddressMapper.INSTANCE.toEntity(request.getAddress()));
        }
        
        if (request.getCompany() != null) {
            user.setCompany(CompanyMapper.INSTANCE.toEntity(request.getCompany()));
        }
        
        return user;
    }
    
    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setWebsite(user.getWebsite());
        
        if (user.getAddress() != null) {
            response.setAddress(AddressMapper.INSTANCE.toResponse(user.getAddress()));
        }
        
        if (user.getCompany() != null) {
            response.setCompany(CompanyMapper.INSTANCE.toResponse(user.getCompany()));
        }
        
        return response;
    }
} 