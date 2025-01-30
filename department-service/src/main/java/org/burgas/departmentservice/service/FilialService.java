package org.burgas.departmentservice.service;

import org.burgas.departmentservice.dto.FilialRequest;
import org.burgas.departmentservice.dto.FilialResponse;
import org.burgas.departmentservice.entity.FilialDepartment;
import org.burgas.departmentservice.exception.FilialNotFoundException;
import org.burgas.departmentservice.exception.NotAuthorizedException;
import org.burgas.departmentservice.handler.RestClientHandler;
import org.burgas.departmentservice.mapper.FilialMapper;
import org.burgas.departmentservice.repository.DepartmentRepository;
import org.burgas.departmentservice.repository.FilialDepartmentRepository;
import org.burgas.departmentservice.repository.FilialRepository;
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
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class FilialService {

    private static final String NOT_AUTHORIZED = "Пользователь не аутентифицирован или не авторизован";
    private static final String FILIAL_DELETED = "Филиал с идентификатором %s успешно удален";
    private static final String FILIAL_NOT_FOUND = "Филиал с идентификатором %s не найден";
    private static final String DEPARTMENT_ADDED = "Отдел добавлен в филиал";
    private static final String DEPARTMENT_REMOVED = "Отдел удален из филиала";
    private static final String FILIAL_OR_DEPARTMENT_NOT_FOUND = "Отдел или филиал отсутствует в базе";

    private final FilialRepository filialRepository;
    private final FilialMapper filialMapper;
    private final RestClientHandler restClientHandler;
    private final DepartmentRepository departmentRepository;
    private final FilialDepartmentRepository filialDepartmentRepository;

    public FilialService(
            FilialRepository filialRepository,
            FilialMapper filialMapper, RestClientHandler restClientHandler,
            DepartmentRepository departmentRepository, FilialDepartmentRepository filialDepartmentRepository
    ) {
        this.filialRepository = filialRepository;
        this.filialMapper = filialMapper;
        this.restClientHandler = restClientHandler;
        this.departmentRepository = departmentRepository;
        this.filialDepartmentRepository = filialDepartmentRepository;
    }

    @Async
    public CompletableFuture<List<FilialResponse>> findAll() {
        return CompletableFuture
                .supplyAsync(filialRepository::findAll)
                .thenApplyAsync(
                        filials -> filials.stream()
                                .map(filialMapper::toFilialResponse)
                                .toList()
                );
    }

    @Async
    public CompletableFuture<FilialResponse> findById(String filialId) {
        return CompletableFuture
                .supplyAsync(() -> filialRepository.findById(Long.valueOf(filialId)))
                .thenApplyAsync(filial -> filial.map(filialMapper::toFilialResponse))
                .thenApplyAsync(filialResponse -> filialResponse.orElseGet(FilialResponse::new));
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
    public CompletableFuture<FilialResponse> createOrUpdate(FilialRequest filialRequest, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getAdmin(authValue))
                .thenApplyAsync(_ -> filialRepository.save(filialMapper.toFilial(filialRequest)))
                .thenApplyAsync(filialMapper::toFilialResponse);
    }

    @Async
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<String> delete(String filialId, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getAdmin(authValue))
                .thenApplyAsync(_ -> filialRepository.findById(Long.valueOf(filialId))
                                .orElseThrow(() -> new FilialNotFoundException(String.format(FILIAL_NOT_FOUND, filialId)))
                )
                .thenAcceptAsync(_ -> filialRepository.deleteById(Long.valueOf(filialId)))
                .thenApplyAsync(_ -> String.format(FILIAL_DELETED, filialId));
    }

    @Async
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<String> addDepartment(String filialId, String departmentId, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getAdmin(authValue))
                .thenApplyAsync(
                        _ -> {
                            if (
                                    filialRepository.existsById(Long.valueOf(filialId)) &&
                                    departmentRepository.existsById(Long.valueOf(departmentId))
                            ) {
                                filialDepartmentRepository.save(
                                        FilialDepartment.builder()
                                                .filialId(Long.valueOf(filialId))
                                                .departmentId(Long.valueOf(departmentId))
                                                .build()
                                );
                                return DEPARTMENT_ADDED;

                            } else
                                throw new RuntimeException(FILIAL_OR_DEPARTMENT_NOT_FOUND);
                        }
                );
    }

    @Async
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<String> removeDepartment(String filialId, String departmentId, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getAdmin(authValue))
                .thenApplyAsync(
                        _ -> {
                            if (
                                    filialRepository.existsById(Long.valueOf(filialId)) &&
                                    departmentRepository.existsById(Long.valueOf(departmentId))
                            ) {
                                filialDepartmentRepository.deleteFilialDepartmentByFilialIdAndDepartmentId(
                                        Long.valueOf(filialId), Long.valueOf(departmentId)
                                );
                                return DEPARTMENT_REMOVED;

                            } else
                                throw new RuntimeException(FILIAL_OR_DEPARTMENT_NOT_FOUND);
                        }
                );
    }
}
