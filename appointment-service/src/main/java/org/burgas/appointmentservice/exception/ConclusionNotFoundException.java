package org.burgas.appointmentservice.exception;

public class ConclusionNotFoundException extends RuntimeException {

    private final String message;

    public ConclusionNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
