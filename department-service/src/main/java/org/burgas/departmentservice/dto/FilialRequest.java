package org.burgas.departmentservice.dto;

import java.time.LocalTime;

public record FilialRequest(
        Long id,
        String address,
        LocalTime open,
        LocalTime close
) {
}
