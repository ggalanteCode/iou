package org.ggalantecode.validator;

import org.ggalantecode.model.CreateIouRequest;

public interface IouValidator {

    void validateIouRequest(CreateIouRequest iouRequest);

}
