package org.burgas.identityservice.dto;

import java.time.LocalDateTime;

public record IdentityRequest(
        Long id,
        String email,
        String phone,
        String password,
        LocalDateTime created,
        Long authorityId
) {
}
