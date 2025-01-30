package org.burgas.appointmentservice.exception;

public class DocumentNotFoundException extends RuntimeException {

    private final String message;

    public DocumentNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
