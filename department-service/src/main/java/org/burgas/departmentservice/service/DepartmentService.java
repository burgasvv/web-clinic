package org.burgas.departmentservice.service;

import org.burgas.departmentservice.dto.DepartmentRequest;
import org.burgas.departmentservice.dto.DepartmentResponse;
import org.burgas.departmentservice.exception.DepartmentNotFoundException;
import org.burgas.departmentservice.exception.NotAuthorizedException;
import org.burgas.departmentservice.handler.RestClientHandler;
import org.burgas.departmentservice.mapper.DepartmentMapper;
import org.burgas.departmentservice.repository.DepartmentRepository;
import org.burgas.identityservice.dto.IdentityPrincipal;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class DepartmentService {

    public static final String NOT_AUTHORIZED = "Пользователь не аутентифицирован или не авторизован";
    public static final String DEPARTMENT_NOT_FOUND = "Отделение с идентификатором %s не найдено";
    public static final String DEPARTMENT_DELETED = "Отделение с идентификатором %s успешно удалено";

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final RestClientHandler restClientHandler;

    public DepartmentService(
            DepartmentRepository departmentRepository,
            DepartmentMapper departmentMapper, RestClientHandler restClientHandler
    ) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.restClientHandler = restClientHandler;
    }

    @Async
    public CompletableFuture<List<DepartmentResponse>> findAll() {
        return CompletableFuture
                .supplyAsync(departmentRepository::findAll)
                .thenApplyAsync(
                        departments -> departments.stream()
                                .map(departmentMapper::toDepartmentResponse)
                                .toList()
                );
    }

    @Async
    public CompletableFuture<List<DepartmentResponse>> findByFilialId(String filialId) {
        return CompletableFuture
                .supplyAsync(() -> departmentRepository.findDepartmentsByFilialId(Long.valueOf(filialId)))
                .thenApplyAsync(
                        departments -> departments.stream()
                                .map(departmentMapper::toDepartmentResponse)
                                .toList()
                );
    }

    @Async
    public CompletableFuture<DepartmentResponse> findById(String departmentId) {
        return CompletableFuture
                .supplyAsync(() -> departmentRepository.findById(Long.valueOf(departmentId)))
                .thenApplyAsync(department -> department.map(departmentMapper::toDepartmentResponse))
                .thenApplyAsync(departmentResponse -> departmentResponse.orElseGet(DepartmentResponse::new));
    }

    private IdentityPrincipal getAdmin(String authValue) {
        return Optional.ofNullable(restClientHandler.getPrincipal(authValue).getBody())
                .filter(identityPrincipal -> identityPrincipal.getAuthenticated() &&
                                             identityPrincipal.getAuthority().equals("ADMIN"))
                .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED));
    }

    @Async
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<DepartmentResponse> createOrUpdate(DepartmentRequest departmentRequest, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getAdmin(authValue))
                .thenApplyAsync(_ -> departmentRepository.save(departmentMapper.toDepartment(departmentRequest)))
                .thenApplyAsync(departmentMapper::toDepartmentResponse);
    }

    @Async
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<String> delete(String departmentId, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getAdmin(authValue))
                .thenApplyAsync(_ -> departmentRepository.findById(Long.valueOf(departmentId))
                                .orElseThrow(() -> new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, departmentId)))
                )
                .thenAcceptAsync(department -> departmentRepository.deleteById(department.getId()))
                .thenApplyAsync(_ -> String.format(DEPARTMENT_DELETED, departmentId));
    }
}
