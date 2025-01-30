package org.burgas.employeeservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.burgas.departmentservice.dto.DepartmentResponse;
import org.burgas.departmentservice.dto.FilialResponse;
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
                .uri("http://localhost:8765/authentication/principal")
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, authValue)
                .retrieve()
                .toEntity(IdentityPrincipal.class);
    }

    @CircuitBreaker(
            name = "getFilialById", fallbackMethod = "fallBackGetFilialById"
    )
    public ResponseEntity<FilialResponse> getFilialById(Long filialId) {
        return restClient.get()
                .uri("http://localhost:8090/filials/{filial-id}", filialId)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity(FilialResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<FilialResponse> fallBackGetFilialById(Throwable throwable) {
        return ResponseEntity.ok(FilialResponse.builder().build());
    }

    @CircuitBreaker(
            name = "getDepartmentById", fallbackMethod = "fallBackGetDepartmentById"
    )
    public ResponseEntity<DepartmentResponse> getDepartmentById(Long departmentId) {
        return restClient.get()
                .uri("http://localhost:8090/departments/{department-id}", departmentId)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity(DepartmentResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<DepartmentResponse> fallBackGetDepartmentById(Throwable throwable) {
        return ResponseEntity.ok(DepartmentResponse.builder().build());
    }
}
