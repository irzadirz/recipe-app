package com.recipee.activity.services.exceptions;

public class InvalidRequestException extends RuntimeException {
    private static final long serialVersionUID = 5162710183389028452L;

    /**
     * Constructs a {@code InvalidRequestException} with no detail message.
     */
    public InvalidRequestException() {
        super();
    }

    /**
     * Constructs a {@code InvalidRequestException} with the specified
     * detail message.
     *
     * @param   message   the detail message.
     */
    public InvalidRequestException(String message) {
        super(message);
    }
}
