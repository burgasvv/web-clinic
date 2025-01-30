package org.burgas.appointmentservice.service;

import org.burgas.appointmentservice.dto.AppointmentRequest;
import org.burgas.appointmentservice.dto.AppointmentResponse;
import org.burgas.appointmentservice.exception.AppointmentNotFoundException;
import org.burgas.appointmentservice.handler.RestClientHandler;
import org.burgas.appointmentservice.mapper.AppointmentMapper;
import org.burgas.appointmentservice.repository.AppointmentRepository;
import org.burgas.employeeservice.exception.EmployeeNotFoundException;
import org.burgas.identityservice.dto.IdentityPrincipal;
import org.burgas.identityservice.exception.NotAuthorizedException;
import org.burgas.patientservice.exception.PatientNotFoundException;
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
public class AppointmentService {

    private static final String APPOINTMENT_NOT_FOUND = "Запись к врачу с идентификатором %s не найдена";
    private static final String APPOINTMENT_RESPONSE_PROBLEM = "Проблемы с преобразование данных ответа";
    private static final String NOT_AUTHORIZED = "Пользователь не аутентифицирован или не авторизован";

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final RestClientHandler restClientHandler;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            AppointmentMapper appointmentMapper, RestClientHandler restClientHandler
    ) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.restClientHandler = restClientHandler;
    }

    @Async
    public CompletableFuture<List<AppointmentResponse>> findAll(String authValue) {
        return CompletableFuture
                .supplyAsync(
                        () -> Optional.ofNullable(restClientHandler.getPrincipal(authValue).getBody())
                                .filter(identityPrincipal -> identityPrincipal.getAuthenticated() &&
                                                             identityPrincipal.getAuthority().equals("ADMIN")
                                )
                                .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED))
                )
                .thenApplyAsync(_ -> appointmentRepository.findAll())
                .thenApplyAsync(
                        appointments -> appointments.stream()
                                .map(appointment -> appointmentMapper.toAppointmentResponse(appointment, authValue)
                                )
                                .toList()
                );
    }

    private IdentityPrincipal getIdentityPrincipal(String authValue) {
        return Optional.ofNullable(restClientHandler.getPrincipal(authValue).getBody())
                .filter(IdentityPrincipal::getAuthenticated)
                .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED));
    }

    @Async
    public CompletableFuture<List<AppointmentResponse>> findByPatientId(String patientId, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getIdentityPrincipal(authValue))
                .thenApplyAsync(
                        identityPrincipal ->
                                Optional.ofNullable(restClientHandler.getPatientById(Long.valueOf(patientId), authValue).getBody())
                                        .filter(patientResponse ->
                                                        identityPrincipal.getId().equals(patientResponse.getIdentityId())
                                        )
                                        .orElseThrow(() -> new PatientNotFoundException("Пациент не найден"))
                )
                .thenApplyAsync(patientResponse -> appointmentRepository.findAppointmentsByPatientId(patientResponse.getId()))
                .thenApplyAsync(
                        appointments -> appointments.stream()
                                .map(appointment -> appointmentMapper.toAppointmentResponse(appointment, authValue)
                                )
                                .toList()
                );
    }

    @Async
    public CompletableFuture<List<AppointmentResponse>> findByEmployeeId(String employeeId, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getIdentityPrincipal(authValue))
                .thenApplyAsync(
                        identityPrincipal ->
                                Optional.ofNullable(restClientHandler.getEmployeeById(Long.valueOf(employeeId)).getBody())
                                        .filter(employeeResponse ->
                                                        identityPrincipal.getId().equals(employeeResponse.getIdentityId())
                                        )
                                        .orElseThrow(() -> new EmployeeNotFoundException("Дактор не найден"))
                )
                .thenApplyAsync(employeeResponse -> appointmentRepository.findAppointmentsByEmployeeId(employeeResponse.getId()))
                .thenApplyAsync(
                        appointments -> appointments.stream()
                                .map(appointment -> appointmentMapper.toAppointmentResponse(appointment, authValue)
                                )
                                .toList()
                );
    }

    @Async
    public CompletableFuture<AppointmentResponse> findById(String appointmentId, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> appointmentRepository.findById(Long.valueOf(appointmentId))
                                .orElseThrow(() -> new AppointmentNotFoundException(
                                        String.format(APPOINTMENT_NOT_FOUND, appointmentId))
                                )
                )
                .thenApplyAsync(appointment -> appointmentMapper.toAppointmentResponse(appointment, authValue))
                .thenApplyAsync(
                        appointmentResponse ->
                            Optional.ofNullable(restClientHandler.getPrincipal(authValue).getBody())
                                    .filter(identityPrincipal -> identityPrincipal.getAuthenticated() &&
                                                                 (identityPrincipal.getAuthority().equals("EMPLOYEE") ||
                                                                  appointmentResponse.getPatient().getId() == Long.parseLong(appointmentId))
                                    )
                                    .map(_ -> appointmentResponse)
                                    .orElseThrow(() -> new RuntimeException(APPOINTMENT_RESPONSE_PROBLEM))
                );
    }

    @Async
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<AppointmentResponse> createOrUpdateByPatient(AppointmentRequest appointmentRequest, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getIdentityPrincipal(authValue))
                .thenApplyAsync(identityPrincipal ->
                                Optional.ofNullable(restClientHandler.getPatientById(appointmentRequest.patientId(), authValue).getBody())
                                        .filter(patientResponse ->
                                                        identityPrincipal.getId().equals(patientResponse.getIdentityId())
                                        )
                                        .orElseThrow(() -> new PatientNotFoundException("Пациент не найден"))
                )
                .thenApplyAsync(_ -> appointmentRepository.save(appointmentMapper.toAppointment(appointmentRequest)))
                .thenApplyAsync(appointment -> appointmentMapper.toAppointmentResponse(appointment, authValue));
    }
}
