package org.burgas.appointmentservice.handler;

import org.burgas.employeeservice.dto.EmployeeResponse;
import org.burgas.identityservice.dto.IdentityPrincipal;
import org.burgas.patientservice.dto.PatientResponse;
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

    public ResponseEntity<EmployeeResponse> getEmployeeById(Long employeeId) {
        return restClient.get()
                .uri("http://localhost:9000/employees/{employee-id}", employeeId)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity(EmployeeResponse.class);
    }

    public ResponseEntity<PatientResponse> getPatientById(Long patientId, String authValue) {
        return restClient.get()
                .uri("http://localhost:9010/patients/by-id/{patient-id}", patientId)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, authValue)
                .retrieve()
                .toEntity(PatientResponse.class);
    }
}
