package org.ggalantecode.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.ggalantecode.entity.UserEntity;

import java.util.List;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<UserEntity> {

    public List<UserEntity> listAllUsers() {
        return listAll(Sort.by("name"));
    }

    public List<UserEntity> listUsersByNames(List<String> userNames) {
        return list("{'name':{$in: [?1]}}", Sort.by("name"), userNames);
    }

}
