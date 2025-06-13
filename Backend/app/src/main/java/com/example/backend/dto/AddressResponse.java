package com.example.backend.dto;

import lombok.Data;

@Data
public class AddressResponse {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private GeoResponse geo;
} 