package org.burgas.appointmentservice.service;

import org.burgas.appointmentservice.dto.ConclusionRequest;
import org.burgas.appointmentservice.dto.ConclusionResponse;
import org.burgas.appointmentservice.exception.AppointmentNotFoundException;
import org.burgas.appointmentservice.exception.ConclusionNotFoundException;
import org.burgas.appointmentservice.handler.RestClientHandler;
import org.burgas.appointmentservice.mapper.AppointmentMapper;
import org.burgas.appointmentservice.mapper.ConclusionMapper;
import org.burgas.appointmentservice.repository.AppointmentRepository;
import org.burgas.appointmentservice.repository.ConclusionRepository;
import org.burgas.identityservice.exception.NotAuthorizedException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ConclusionService {

    public static final String CONCLUSION_NOT_FOUND = "Заключение врача не найдено";
    public static final String NOT_AUTHORIZED = "Пользователь не аутентифицирован или не авторизован";
    public static final String APPOINTMENT_NOT_FOUND = "Запись к врачу не найдена";

    private final ConclusionRepository conclusionRepository;
    private final ConclusionMapper conclusionMapper;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final RestClientHandler restClientHandler;

    public ConclusionService(
            ConclusionRepository conclusionRepository, ConclusionMapper conclusionMapper,
            AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper,
            RestClientHandler restClientHandler
    ) {
        this.conclusionRepository = conclusionRepository;
        this.conclusionMapper = conclusionMapper;
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.restClientHandler = restClientHandler;
    }

    @Async
    public CompletableFuture<ConclusionResponse> findById(String conclusionId, String authValue) {
        return CompletableFuture
                .supplyAsync(
                        () -> conclusionRepository.findById(Long.valueOf(conclusionId))
                                .map(conclusion -> conclusionMapper.toConclusionResponse(conclusion, authValue)
                                )
                                .orElseThrow(() -> new ConclusionNotFoundException(CONCLUSION_NOT_FOUND))
                )
                .thenApplyAsync(
                        conclusionResponse ->
                                Optional.ofNullable(restClientHandler.getPrincipal(authValue).getBody())
                                        .filter(identityPrincipal ->
                                                identityPrincipal.getAuthenticated() &&
                                                (identityPrincipal.getAuthority().equals("EMPLOYEE") ||
                                                 conclusionResponse.getAppointment().getPatient().getIdentityId().equals(identityPrincipal.getId())
                                                )
                                        )
                                        .map(_ -> conclusionResponse)
                                        .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED))
                );
    }

    @Async
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<ConclusionResponse> createOrUpdate(ConclusionRequest conclusionRequest, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> appointmentRepository.findById(conclusionRequest.appointmentId())
                                .map(appointment -> appointmentMapper.toAppointmentResponse(appointment, authValue)
                                )
                                .orElseThrow(() -> new AppointmentNotFoundException(APPOINTMENT_NOT_FOUND))
                )
                .thenApplyAsync(appointmentResponse ->
                                Optional.ofNullable(restClientHandler.getPrincipal(authValue).getBody())
                                        .filter(identityPrincipal ->
                                                identityPrincipal.getAuthenticated() &&
                                                identityPrincipal.getId().equals(appointmentResponse.getEmployee().getIdentityId())
                                        )
                                        .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED))
                )
                .thenApplyAsync(_ -> conclusionRepository.save(conclusionMapper.toConclusion(conclusionRequest)))
                .thenApplyAsync(conclusion -> conclusionMapper.toConclusionResponse(conclusion, authValue));
    }
}
