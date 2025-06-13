package com.example.backend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;
    
    @Column(name = "user_name")
    private String name;
    
    private String username;
    private String email;
    
    @Embedded
    private Address address;
    
    private String phone;
    private String website;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "company_name")),
        @AttributeOverride(name = "catchPhrase", column = @Column(name = "company_catch_phrase")),
        @AttributeOverride(name = "bs", column = @Column(name = "company_bs"))
    })
    private Company company;
} 