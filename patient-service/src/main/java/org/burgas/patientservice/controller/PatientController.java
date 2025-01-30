package org.burgas.patientservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.burgas.patientservice.dto.PatientRequest;
import org.burgas.patientservice.dto.PatientResponse;
import org.burgas.patientservice.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientResponse>> getPatients(HttpServletRequest request) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(patientService.findAll(request.getHeader(AUTHORIZATION)).get());
    }

    @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientResponse>> getPatientsByFlp(@RequestParam String flp, HttpServletRequest request)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(patientService.findByFlp(flp, request.getHeader(AUTHORIZATION)).get());
    }

    @GetMapping(value = "/by-id/{patient-id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable("patient-id") Long patientId, HttpServletRequest request)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(patientService.findById(patientId, request.getHeader(AUTHORIZATION)).get());
    }

    @GetMapping(value = "/by-identity/{identity-id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientResponse> getPatientByIdentityId(@PathVariable("identity-id") Long identityId)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(patientService.findByIdentityId(identityId).get());
    }

    @PostMapping(
            value = "/create",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PatientResponse> createPatient(@RequestBody PatientRequest patientRequest)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(patientService.createOrUpdate(patientRequest).get());
    }

    @PutMapping(
            value = "/update",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PatientResponse> updatePatient(@RequestBody PatientRequest patientRequest)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(patientService.createOrUpdate(patientRequest).get());
    }

    @DeleteMapping(value = "/delete", produces = TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deletePatient(@RequestParam Long patientId)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(patientService.delete(patientId).get());
    }
}
