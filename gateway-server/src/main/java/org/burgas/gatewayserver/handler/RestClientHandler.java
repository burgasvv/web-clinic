package org.burgas.gatewayserver.handler;

import org.burgas.gatewayserver.dto.IdentityResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientHandler {

    private final RestClient restClient;

    public RestClientHandler(RestClient restClient) {
        this.restClient = restClient;
    }

    public ResponseEntity<IdentityResponse> getIdentityByEmail(String email) {
        return restClient.get()
                .uri("http://localhost:8888/identities/by-email/{email}", email)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(IdentityResponse.class);
    }
}
