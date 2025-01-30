package org.burgas.employeeservice.service;

import org.burgas.employeeservice.dto.PositionRequest;
import org.burgas.employeeservice.dto.PositionResponse;
import org.burgas.employeeservice.exception.NotAuthorizedException;
import org.burgas.employeeservice.handler.RestClientHandler;
import org.burgas.employeeservice.mapper.PositionMapper;
import org.burgas.employeeservice.repository.PositionRepository;
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
public class PositionService {

    public static final String NOT_AUTHORIZED = "Пользователь не аутентифицирован или не авторизован";
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final RestClientHandler restClientHandler;

    public PositionService(
            PositionRepository positionRepository,
            PositionMapper positionMapper, RestClientHandler restClientHandler
    ) {
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
        this.restClientHandler = restClientHandler;
    }

    @Async
    public CompletableFuture<List<PositionResponse>> findAll() {
        return CompletableFuture
                .supplyAsync(positionRepository::findAll)
                .thenApplyAsync(
                        positions -> positions.stream()
                                .map(positionMapper::toPositionResponse)
                                .toList()
                );
    }

    @Async
    public CompletableFuture<PositionResponse> findById(Long positionId) {
        return CompletableFuture
                .supplyAsync(() -> positionRepository.findById(positionId))
                .thenApplyAsync(position -> position.map(positionMapper::toPositionResponse))
                .thenApplyAsync(positionResponse -> positionResponse.orElseGet(PositionResponse::new));
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
    public CompletableFuture<PositionResponse> createOrUpdate(PositionRequest positionRequest, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getAdmin(authValue))
                .thenApplyAsync(_ -> positionRepository.save(positionMapper.toPosition(positionRequest)))
                .thenApplyAsync(positionMapper::toPositionResponse);
    }
}
