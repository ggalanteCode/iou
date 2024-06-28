package org.ggalantecode.exceptions;

public class NotValidInputException extends RuntimeException {

    public NotValidInputException() {}

    public NotValidInputException(String message) {
        super(message);
    }
}
