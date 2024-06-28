package org.ggalantecode.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@MongoEntity(collection = "user")
public class UserEntity extends PanacheMongoEntity {

    private String userId;

    private Map<String, Double> owes;

    private Map<String, Double> owed_by;

    private Double balance;

}
