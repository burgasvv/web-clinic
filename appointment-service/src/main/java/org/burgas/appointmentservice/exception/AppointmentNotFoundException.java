package org.burgas.appointmentservice.exception;

public class AppointmentNotFoundException extends RuntimeException {

    private final String message;

    public AppointmentNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
