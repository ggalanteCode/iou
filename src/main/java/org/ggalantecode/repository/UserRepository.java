package org.ggalantecode.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ggalantecode.entity.UserEntity;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<UserEntity> {
}
