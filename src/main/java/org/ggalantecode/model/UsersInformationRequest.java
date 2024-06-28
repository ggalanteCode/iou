package org.ggalantecode.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsersInformationRequest {

    private List<String> userIds;

}
