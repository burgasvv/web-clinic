package org.burgas.patientservice.exception;

public class PatientNotFoundException extends RuntimeException{

    private final String message;

    public PatientNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
