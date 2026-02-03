package com.example.identityservice.mapper;

import com.example.identityservice.dto.response.UserRegistrationResponseDto;
import com.example.identityservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserRegistrationMapper {
    UserRegistrationResponseDto toUserRegistrationResponseDto(User user);
}
