package org.ggalantecode.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<Response> {

    private Response data;

}
