package org.ggalantecode.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.ggalantecode.entity.UserEntity;
import org.ggalantecode.mapper.UserMapper;
import org.ggalantecode.model.*;
import org.ggalantecode.service.IouService;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

/*
    https://exercism.org/tracks/java/exercises/rest-api
    https://developers.redhat.com/articles/2023/11/23/getting-started-mongodb-and-quarkus
    https://quarkus.io/guides/mongodb-panache
 */
@Path("/v1/iou")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class IouResource {

    @Inject
    IouService iouService;

    @POST
    @Path("/add")
    public BaseResponse<UserResponse> createUser(BaseRequest<CreateUserRequest> req) {
        UserEntity user = UserMapper.INSTANCE.createUserRequestToUserEntity(req.getData());
        user = iouService.createUser(user);
        UserResponse res = UserMapper.INSTANCE.userEntityToUserResponse(user);
        return new BaseResponse<>(res);
    }

    @GET
    @Path("/users")
    public BaseResponse<List<UserResponse>> readUserInformation(BaseRequest<UsersInformationRequest> req) {
        List<UserEntity> users;
        List<UserResponse> res;
        if(req == null) {
            users = iouService.getAllUsers();
        } else {
            users = iouService.getUsersByName(req.getData().getUserIds());
        }
        res = UserMapper.INSTANCE.userEntitiesToUsersResponse(users);
        return new BaseResponse<>(res);
    }

    @POST
    @Path("/iou")
    public BaseResponse<List<UserResponse>> createIou(BaseRequest<CreateIouRequest> req) {
        List<UserEntity> updatedUsers = iouService.createIou(
                req.getData().getLenderId(), req.getData().getBorrowerId(), req.getData().getAmount());
        List<UserResponse> res = UserMapper.INSTANCE.userEntitiesToUsersResponse(updatedUsers);
        return new BaseResponse<>(res);
    }
}
