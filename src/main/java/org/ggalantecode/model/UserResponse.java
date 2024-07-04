package org.ggalantecode.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UserResponse {

    private String name;

    private Map<String, Double> owes;

    private Map<String, Double> owed_by;

    private Double balance;

}
