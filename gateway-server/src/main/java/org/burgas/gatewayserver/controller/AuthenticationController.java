package org.burgas.gatewayserver.controller;

import org.burgas.gatewayserver.dto.IdentityPrincipal;
import org.burgas.gatewayserver.dto.IdentityResponse;
import org.burgas.gatewayserver.mapper.IdentityPrincipalMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final IdentityPrincipalMapper identityPrincipalMapper;

    public AuthenticationController(IdentityPrincipalMapper identityPrincipalMapper) {
        this.identityPrincipalMapper = identityPrincipalMapper;
    }

    @GetMapping(value = "/principal", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IdentityPrincipal> getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            return ResponseEntity.ok(
                    identityPrincipalMapper.toIdentityPrincipal(
                    (IdentityResponse) authentication.getPrincipal(), true)
            );
        else
            return ResponseEntity.ok(IdentityPrincipal.builder().authenticated(false).build());
    }
}
