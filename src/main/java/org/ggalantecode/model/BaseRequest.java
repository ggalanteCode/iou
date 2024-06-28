package org.ggalantecode.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseRequest<Request> {

    private Request data;

}
