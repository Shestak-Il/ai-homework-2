package com.example.backend.mapper;

import com.example.backend.Company;
import com.example.backend.dto.CompanyRequest;
import com.example.backend.dto.CompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);
    
    Company toEntity(CompanyRequest request);
    CompanyResponse toResponse(Company company);
} 