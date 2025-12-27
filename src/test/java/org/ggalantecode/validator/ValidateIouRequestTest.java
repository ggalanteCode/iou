package org.ggalantecode.validator;

import org.ggalantecode.exceptions.NotValidInputException;
import org.ggalantecode.model.CreateIouRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateIouRequestTest {

    IouValidator iouValidator = new IouValidatorImpl();

    @Test
    void givenCreateIouRequest_whenAmountIsNull_thenThrowNullPointerException() {

        CreateIouRequest request = new CreateIouRequest();

        request.setAmount(null);

        assertThrows(NullPointerException.class, () -> iouValidator.validateIouRequest(request));

    }

    @Test
    void givenCreateIouRequest_whenAmountIsZero_thenThrowNotValidInputException() {

        CreateIouRequest request = new CreateIouRequest();

        request.setAmount(0.0);

        assertThrows(NotValidInputException.class, () -> iouValidator.validateIouRequest(request));

    }

    @Test
    void givenCreateIouRequest_whenAmountIsNotZeroAndLenderAndBorrowerHaveTheSameName_thenThrowNotValidInputException() {

        CreateIouRequest request = new CreateIouRequest();

        request.setAmount(0.1);
        request.setLenderId("Giovanni");
        request.setBorrowerId("Giovanni");

        assertThrows(NotValidInputException.class, () -> iouValidator.validateIouRequest(request));

    }

    @Test
    void givenCreateIouRequest_whenAmountIsNotZeroAndLenderAndBorrowerHaveDifferentNames_thenNoExceptionIsThrown() {

        CreateIouRequest request = new CreateIouRequest();

        request.setAmount(0.1);
        request.setLenderId("Giovanni");
        request.setBorrowerId("Galante");

        assertDoesNotThrow(() -> iouValidator.validateIouRequest(request));

    }

}