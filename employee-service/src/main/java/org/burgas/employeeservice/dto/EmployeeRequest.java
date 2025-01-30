package org.burgas.employeeservice.dto;

import org.burgas.employeeservice.entity.Gender;

import java.time.LocalDate;
import java.util.List;

public record EmployeeRequest(
        Long id,
        String firstname,
        String lastname,
        String patronymic,
        Gender gender,
        LocalDate birthdate,
        String about,
        Long identityId,
        Long filialId,
        Long departmentId,
        List<PositionRequest> positions
) {
}
