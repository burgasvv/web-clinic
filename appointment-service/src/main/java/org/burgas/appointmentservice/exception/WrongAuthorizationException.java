package org.burgas.appointmentservice.exception;

public class WrongAuthorizationException extends RuntimeException {

    private final String message;

    public WrongAuthorizationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
