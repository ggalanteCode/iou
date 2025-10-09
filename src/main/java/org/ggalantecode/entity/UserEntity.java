package org.ggalantecode.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@MongoEntity(collection = "user")
public class UserEntity extends PanacheMongoEntity {

    private String name;

    private Map<String, Double> owes;

    private Map<String, Double> owed_by;

    private Double balance;

}
