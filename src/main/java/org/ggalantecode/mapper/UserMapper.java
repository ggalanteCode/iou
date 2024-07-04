package org.ggalantecode.mapper;

import org.ggalantecode.entity.UserEntity;
import org.ggalantecode.model.CreateUserRequest;
import org.ggalantecode.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "userId", target = "userId")
    UserEntity createUserRequestToUserEntity(CreateUserRequest createUserRequest);

    UserResponse userEntityToUserResponse(UserEntity userEntity);

    List<UserResponse> userEntitiesToUsersResponse(List<UserEntity> userEntities);

}
