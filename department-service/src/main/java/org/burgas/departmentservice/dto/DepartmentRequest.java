package org.burgas.departmentservice.dto;

public record DepartmentRequest(
        Long id,
        String name,
        String description
) {
}
