package org.ggalantecode.mapper;

import org.ggalantecode.entity.UserEntity;
import org.ggalantecode.model.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "name", target = "name")
    UserEntity createUserRequestToUserEntity(CreateUserRequest createUserRequest);

    UserResponse userEntityToUserResponse(UserEntity userEntity);

    List<UserResponse> userEntitiesToUsersResponse(List<UserEntity> userEntities);

}
