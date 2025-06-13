package com.example.backend.dto;

import lombok.Data;

@Data
public class AddressRequest {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private GeoRequest geo;
} 