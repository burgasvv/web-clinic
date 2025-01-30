package org.burgas.appointmentservice.dto;

public record ConclusionRequest(
        Long id,
        Long appointmentId,
        Long documentId
) {
}
