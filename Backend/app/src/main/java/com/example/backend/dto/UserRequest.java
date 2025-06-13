package com.example.backend.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String username;
    private String email;
    private AddressRequest address;
    private String phone;
    private String website;
    private CompanyRequest company;
} 