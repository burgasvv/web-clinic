package org.burgas.patientservice.service;

import org.burgas.identityservice.dto.IdentityPrincipal;
import org.burgas.patientservice.exception.NotAuthorizedException;
import org.burgas.patientservice.dto.PatientRequest;
import org.burgas.patientservice.dto.PatientResponse;
import org.burgas.patientservice.exception.PatientNotFoundException;
import org.burgas.patientservice.handler.RestClientHandler;
import org.burgas.patientservice.mapper.PatientMapper;
import org.burgas.patientservice.repository.PatientRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PatientService {

    private static final String PATIENT_NOT_FOUND = "Пользователь с идентификатором %d не найден";
    private static final String PATIENT_DELETED = "Пользователь с идентификатором %d удален";
    public static final String NOT_AUTHORIZED = "Пользователь не аутентифицирован или не авторизован";

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final RestClientHandler restClientHandler;

    public PatientService(
            PatientRepository patientRepository,
            PatientMapper patientMapper, RestClientHandler restClientHandler
    ) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.restClientHandler = restClientHandler;
    }

    private IdentityPrincipal getIdentityPrincipal(String authValue) {
        return Optional.ofNullable(restClientHandler.getPrincipal(authValue).getBody())
                .filter(identityPrincipal -> identityPrincipal.getAuthenticated() &&
                                             (identityPrincipal.getAuthority().equals("ADMIN") ||
                                              identityPrincipal.getAuthority().equals("EMPLOYEE"))
                )
                .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED));
    }

    @Async
    public CompletableFuture<List<PatientResponse>> findAll(String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getIdentityPrincipal(authValue))
                .thenApplyAsync(_ -> patientRepository.findAll())
                .thenApplyAsync(
                        patients -> patients.stream()
                                .map(patientMapper::toPatientResponse)
                                .toList()
                );
    }

    @Async
    public CompletableFuture<List<PatientResponse>> findByFlp(String flp, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getIdentityPrincipal(authValue))
                .thenApplyAsync(_ -> patientRepository.findPatientsByFlp(flp))
                .thenApplyAsync(
                        patients -> patients.stream()
                                .map(patientMapper::toPatientResponse)
                                .toList()
                );
    }

    @Async
    public CompletableFuture<PatientResponse> findById(Long patientId, String authValue) {
        return CompletableFuture
                .supplyAsync(
                        () -> patientRepository.findById(patientId)
                                .orElseThrow(() -> new PatientNotFoundException(PATIENT_NOT_FOUND))
                )
                .thenApplyAsync(
                        patient -> Optional.ofNullable(restClientHandler.getPrincipal(authValue).getBody())
                                .filter(identityPrincipal -> identityPrincipal.getAuthenticated() &&
                                                             (identityPrincipal.getId().equals(patient.getIdentityId()) ||
                                                              identityPrincipal.getAuthority().equals("EMPLOYEE"))
                                )
                                .map(_ -> patient)
                                .orElseThrow(() -> new PatientNotFoundException(NOT_AUTHORIZED))
                )
                .thenApplyAsync(patientMapper::toPatientResponse);
    }

    @Async
    public CompletableFuture<PatientResponse> findByIdentityId(Long identityId) {
        return CompletableFuture
                .supplyAsync(() -> patientRepository.findPatientByIdentityId(identityId))
                .thenApplyAsync(patient -> patient.map(patientMapper::toPatientResponse))
                .thenApplyAsync(
                        patientResponse ->
                                patientResponse.orElseThrow(() -> new PatientNotFoundException(PATIENT_NOT_FOUND))
                );
    }

    @Async
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<PatientResponse> createOrUpdate(PatientRequest patientRequest) {
        return CompletableFuture.supplyAsync(() -> patientRepository.save(patientMapper.toPatient(patientRequest)))
                .thenApplyAsync(patientMapper::toPatientResponse);
    }

    @Async
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<String> delete(Long patientId) {
        return CompletableFuture.supplyAsync(
                () -> patientRepository.findById(patientId)
                        .orElseThrow(() -> new RuntimeException(String.format(PATIENT_NOT_FOUND, patientId)))
        )
                .thenAcceptAsync(patient -> patientRepository.deleteById(patient.getId()))
                .thenApplyAsync(_ -> String.format(PATIENT_DELETED, patientId));

    }
}
