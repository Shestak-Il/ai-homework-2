package com.example.backend.mapper;

import com.example.backend.Address;
import com.example.backend.dto.AddressRequest;
import com.example.backend.dto.AddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
    
    Address toEntity(AddressRequest request);
    AddressResponse toResponse(Address address);
} 