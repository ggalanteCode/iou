package org.ggalantecode.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateIouRequest {

    private String lenderId;

    private String borrowerId;

    private Double amount;

}
