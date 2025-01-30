package org.burgas.employeeservice.service;

import org.burgas.employeeservice.dto.EmployeeRequest;
import org.burgas.employeeservice.dto.EmployeeResponse;
import org.burgas.employeeservice.entity.Employee;
import org.burgas.employeeservice.entity.Position;
import org.burgas.employeeservice.entity.PositionEmployee;
import org.burgas.employeeservice.entity.PositionEmployeePK;
import org.burgas.employeeservice.exception.EmployeeNotFoundException;
import org.burgas.employeeservice.exception.NotAuthorizedException;
import org.burgas.employeeservice.handler.RestClientHandler;
import org.burgas.employeeservice.mapper.EmployeeMapper;
import org.burgas.employeeservice.repository.EmployeeRepository;
import org.burgas.employeeservice.repository.PositionEmployeeRepository;
import org.burgas.employeeservice.repository.PositionRepository;
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
public class EmployeeService {

    public static final String NOT_AUTHORIZED = "Пользователь не аутентифицирован или не авторизован";
    public static final String EMPLOYEE_NOT_FOUND = "Пользователь с идентификатором %d не найден";
    public static final String EMPLOYEE_DELETED = "Пользователь с идентификатором %d удален";

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final RestClientHandler restClientHandler;
    private final PositionEmployeeRepository positionEmployeeRepository;
    private final PositionRepository positionRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, RestClientHandler restClientHandler,
            PositionEmployeeRepository positionEmployeeRepository, PositionRepository positionRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.restClientHandler = restClientHandler;
        this.positionEmployeeRepository = positionEmployeeRepository;
        this.positionRepository = positionRepository;
    }

    @Async
    public CompletableFuture<List<EmployeeResponse>> findAll() {
        return CompletableFuture
                .supplyAsync(employeeRepository::findAll)
                .thenApplyAsync(
                        employees -> employees.stream()
                                .map(employeeMapper::toEmployeeResponse)
                                .toList()
                );
    }

    @Async
    public CompletableFuture<EmployeeResponse> findById(Long employeeId) {
        return CompletableFuture
                .supplyAsync(() -> employeeRepository.findById(employeeId))
                .thenApplyAsync(employee -> employee.map(employeeMapper::toEmployeeResponse))
                .thenApplyAsync(employeeResponse -> employeeResponse.orElseGet(EmployeeResponse::new));
    }

    private void getIdentityPrincipal(Long identityId, String authValue) {
        Optional.ofNullable(restClientHandler.getPrincipal(authValue).getBody())
                .filter(identityPrincipal -> identityPrincipal.getAuthenticated() &&
                                             identityPrincipal.getId().equals(identityId) &&
                                             (identityPrincipal.getAuthority().equals("ADMIN") || identityPrincipal.getAuthority().equals("EMPLOYEE"))
                )
                .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED));
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public EmployeeResponse createOrUpdate(EmployeeRequest employeeRequest, String authValue) {
        getIdentityPrincipal(employeeRequest.identityId(), authValue);
        Employee employee = employeeRepository.save(employeeMapper.toEmployee(employeeRequest));
        List<Position> positionsByEmployeeId = positionRepository.findPositionsByEmployeeId(employee.getId());
        positionsByEmployeeId.forEach(
                position -> positionEmployeeRepository.deleteById(
                        PositionEmployeePK.builder()
                                .positionId(position.getId())
                                .employeeId(employee.getId())
                                .build()
                )
        );
        employeeRequest.positions().forEach(
                positionRequest ->
                        positionEmployeeRepository.save(
                                PositionEmployee.builder()
                                        .positionId(positionRequest.id())
                                        .employeeId(employee.getId())
                                        .build()
                        )
        );
        return employeeRepository.findById(employee.getId())
                .map(employeeMapper::toEmployeeResponse)
                .orElseThrow(
                        () -> new EmployeeNotFoundException(
                                String.format(EMPLOYEE_NOT_FOUND, employeeRequest.id())
                        )
                );

    }



    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String delete(Long employeeId, String authValue) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format(EMPLOYEE_NOT_FOUND, employeeId)));
        getIdentityPrincipal(employee.getIdentityId(), authValue);
        positionEmployeeRepository.deletePositionEmployeeByEmployeeId(employee.getId());
        employeeRepository.deleteById(employee.getId());
        return String.format(EMPLOYEE_DELETED, employeeId);
    }
}
