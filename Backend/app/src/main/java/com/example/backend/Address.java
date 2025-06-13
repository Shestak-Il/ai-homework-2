package com.example.backend;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    @Embedded
    private Geo geo;
} 