package com.swd.pregnancycare.mapper;

import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "role.name", target = "roleName") // Lấy tên Role thay vì object RoleEntity
     // Bỏ qua password
    UserResponse toUserResponse(UserEntity user);
}
