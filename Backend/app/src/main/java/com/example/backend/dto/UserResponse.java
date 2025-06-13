package com.example.backend.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String username;
    private String email;
    private AddressResponse address;
    private String phone;
    private String website;
    private CompanyResponse company;
} 