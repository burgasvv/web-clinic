package org.burgas.appointmentservice.dto;

import org.burgas.patientservice.entity.Gender;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequest(
        Long id,
        String firstname,
        String lastname,
        String patronymic,
        Gender gender,
        String description,
        LocalDate date,
        LocalTime hours,
        Long employeeId,
        Long patientId,
        Boolean closed
) {
}
