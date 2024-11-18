package com.founderz.auth.application.impl;

import com.founderz.auth.application.dto.AuthApplicationDto;
import com.founderz.auth.application.dto.RegisterDto;
import com.founderz.common.crypto.PasswordEncoder;
import com.founderz.common.exception.ServerException;
import com.founderz.user.domain.dto.UserDomainDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(
        componentModel = SPRING,
        unmappedTargetPolicy = ERROR
)
abstract class AuthApplicationMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountId", source = "accountId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "securedPassword", expression = "java(SecuredPassword.create(passwordEncoder.encode(dto.password())))")
    protected abstract UserDomainDto mapToDomainDto(RegisterDto dto);

    @Mapping(target = "accountId", source = "accountId")
    @Mapping(target = "securedPassword", source = "securedPassword")
    protected abstract AuthApplicationDto mapToApplicationDto(UserDomainDto dto);

    public UserDomainDto toDomainDto(RegisterDto dto) {
        try {
            return mapToDomainDto(dto);
        } catch(Exception e) {
            throw new ServerException(e);
        }
    }

    public AuthApplicationDto toApplicationDto(UserDomainDto dto) {
        try {
            return mapToApplicationDto(dto);
        } catch(Exception e) {
            throw new ServerException(e);
        }
    }
}