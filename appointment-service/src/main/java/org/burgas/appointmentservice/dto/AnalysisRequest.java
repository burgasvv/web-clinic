package org.burgas.appointmentservice.dto;

public record AnalysisRequest(
        Long id,
        Long patientId,
        Long employeeId,
        Long documentId
) {
}
