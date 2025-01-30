package org.burgas.patientservice.dto;

import org.burgas.patientservice.entity.Gender;

import java.time.LocalDate;

public record PatientRequest(
        Long id,
        String firstname,
        String lastname,
        String patronymic,
        Gender gender,
        LocalDate birthdate,
        Long identityId
) {
}
