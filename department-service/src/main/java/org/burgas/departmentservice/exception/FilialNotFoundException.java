package org.burgas.departmentservice.exception;

public class FilialNotFoundException extends RuntimeException{

    private final String message;

    public FilialNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
