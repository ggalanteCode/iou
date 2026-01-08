package org.ggalantecode.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.ggalantecode.entity.UserEntity;
import org.ggalantecode.exceptions.UserNotFoundException;

import java.util.List;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<UserEntity> {

    public List<UserEntity> listAllUsers() {
        return listAll(Sort.by("name"));
    }

    public List<UserEntity> listUsersByNames(List<String> userNames) {
        return list("{'name':{$in: [?1]}}", Sort.by("name"), userNames);
    }

    public UserEntity findUserByName(String userName) {
        return find("name", userName).firstResultOptional().orElseThrow(
                () -> new UserNotFoundException("user \"" + userName + "\" not found")
        );
    }

}
