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

    /**
     *
     * Creates a new user and returns it in the response. In addition to the specified name, the new user has by default
     * an initial balance of 0 and has no associated lenders or borrowers.
     * There cannot be two users with the same name.
     *
     * @param req the name of the new user
     * @return the new user.
     */
    @POST
    @Path("/add")
    public BaseResponse<UserResponse> createUser(BaseRequest<CreateUserRequest> req) {
        UserEntity user = UserMapper.INSTANCE.createUserRequestToUserEntity(req.getData());
        user = iouService.createUser(user);
        UserResponse res = UserMapper.INSTANCE.userEntityToUserResponse(user);
        return new BaseResponse<>(res);
    }

    /**
     *
     * If no username is specified, it returns all existing users, otherwise it returns all existing users with the
     * names specified in the request.
     *
     * @param req the names of the specified users. It is not mandatory to specify them.
     * @return all existing users or all existing users with the specified names.
     */
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

    /**
     *
     * Creates a new IOU (an acronym that stands for 'I Owe You', that is a written promise to pay back a debt).
     * The 'promise' is between two users, one of whom plays the role of lender, and the other the role of borrower;
     * the borrower promises to return the lender the amount specified in the promise; furthermore, the lender's and the
     * borrower's balance is updated accordingly.
     *
     * @param req the lender's name, the borrower's name and the amount that the borrower promises to pay back to the
     *            lender
     * @return the two users involved in the promise, with their lenders, borrowers and balances updated.
     */
    @POST
    @Path("/iou")
    public BaseResponse<List<UserResponse>> createIou(BaseRequest<CreateIouRequest> req) {
        List<UserEntity> updatedUsers = iouService.createIou(
                req.getData().getLenderId(), req.getData().getBorrowerId(), req.getData().getAmount());
        List<UserResponse> res = UserMapper.INSTANCE.userEntitiesToUsersResponse(updatedUsers);
        return new BaseResponse<>(res);
    }
}
