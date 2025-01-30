package org.burgas.patientservice.handler;

import org.burgas.identityservice.dto.IdentityPrincipal;
import org.burgas.identityservice.dto.IdentityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class RestClientHandler {

    private final RestClient restClient;

    public RestClientHandler(RestClient restClient) {
        this.restClient = restClient;
    }

    public ResponseEntity<IdentityPrincipal> getPrincipal(String authValue) {
        return restClient.get()
                .uri("http://localhost:8765/authentication/principal")
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, authValue)
                .retrieve()
                .toEntity(IdentityPrincipal.class);
    }

    @SuppressWarnings("unused")
    public ResponseEntity<IdentityResponse> getIdentity(Long identityId) {
        return restClient.get()
                .uri("http://localhost:8888/identities/{identity-id}", identityId)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity(IdentityResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityResponse> fallBackGetIdentity(Throwable throwable) {
        return ResponseEntity.ok(IdentityResponse.builder().build());
    }
}
