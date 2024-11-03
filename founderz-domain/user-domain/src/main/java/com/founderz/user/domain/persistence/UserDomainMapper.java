package com.founderz.user.domain.persistence;

import com.founderz.user.domain.dto.UserDomainDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

import static org.mapstruct.ReportingPolicy.ERROR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ERROR)
interface UserDomainMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountId", expression = "java(dto.accountId().accountId())")
    @Mapping(target = "name", expression = "java(dto.name().name())")
    @Mapping(target = "type", expression = "java(UserEntity.Type.valueOf(dto.type().type()))")
    @Mapping(target = "phoneNumber", expression = "java(dto.phoneNumber().phoneNumber())")
    @Mapping(target = "password", expression = "java(dto.securedPassword().password())")
    UserEntity toEntity(UserDomainDto dto);

    @Mapping(target = "id", expression = "java(UserId.create(entity.getId()))")
    @Mapping(target = "accountId", expression = "java(AccountId.create(entity.getAccountId()))")
    @Mapping(target = "name", expression = "java(AccountName.create(entity.getName()))")
    @Mapping(target = "type", expression = "java(AccountType.create(entity.getType().name()))")
    @Mapping(target = "phoneNumber", expression = "java(PhoneNumber.create(entity.getPhoneNumber()))")
    @Mapping(target = "securedPassword", expression = "java(SecuredPassword.create(entity.getPassword()))")
    UserDomainDto toDto(UserEntity entity);

    default Optional<UserDomainDto> toOptionalDto(Optional<UserEntity> entity) {
        return entity.map(this::toDto);
    }
}