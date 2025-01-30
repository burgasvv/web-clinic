package org.burgas.departmentservice.handler;

import org.burgas.identityservice.dto.IdentityPrincipal;
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
                .uri("http://localhost:8765/authentication/principal", authValue)
                .header(AUTHORIZATION, authValue)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity(IdentityPrincipal.class);
    }
}
