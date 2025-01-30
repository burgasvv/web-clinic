package org.burgas.identityservice.service;

import org.burgas.identityservice.dto.IdentityPrincipal;
import org.burgas.identityservice.dto.IdentityRequest;
import org.burgas.identityservice.dto.IdentityResponse;
import org.burgas.identityservice.exception.IdentityNotFoundException;
import org.burgas.identityservice.exception.NotAuthorizedException;
import org.burgas.identityservice.handler.RestClientHandler;
import org.burgas.identityservice.mapper.IdentityMapper;
import org.burgas.identityservice.repository.IdentityRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class IdentityService {

    public static final String NOT_AUTHORIZED = "Пользователь не аутентифицирован или не авторизован";
    private static final String IDENTITY_NOT_FOUND = "Пользователь с идентификатором %d не найден";
    private static final String IDENTITY_DELETED = "Пользователь с идентификатором %d успешно удален";

    private final IdentityRepository identityRepository;
    private final IdentityMapper identityMapper;
    private final RestClientHandler restClientHandler;

    public IdentityService(
            IdentityRepository identityRepository,
            IdentityMapper identityMapper, RestClientHandler restClientHandler
    ) {
        this.identityRepository = identityRepository;
        this.identityMapper = identityMapper;
        this.restClientHandler = restClientHandler;
    }

    public List<IdentityResponse> findAllSync(String authValue) {
        //noinspection unused
        IdentityPrincipal admin = getAdmin(authValue);
        return identityRepository.findAll()
                .stream()
                .map(identityMapper::toIdentityResponse)
                .toList();
    }

    @Async
    public CompletableFuture<List<IdentityResponse>> findAll(String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getAdmin(authValue))
                .thenApplyAsync(_ -> identityRepository.findAll())
                .thenApplyAsync(
                        identities -> identities.stream()
                                .map(identityMapper::toIdentityResponse)
                                .toList()
                );
    }

    private IdentityPrincipal getAdmin(String authValue) {
        return Optional.ofNullable(restClientHandler.getPrincipal(authValue).getBody())
                .filter(identityPrincipal -> identityPrincipal.getAuthenticated() &&
                                             identityPrincipal.getAuthority().equals("ADMIN"))
                .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED));
    }

    @Async
    public CompletableFuture<IdentityResponse> findById(Long identityId, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> identityRepository.findById(identityId))
                .thenApplyAsync(
                        identity -> identity.filter(
                                        tempIdentity -> {
                                            IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authValue).getBody();
                                            return Objects.requireNonNull(identityPrincipal).getAuthenticated() && (
                                                    identityPrincipal.getId().equals(tempIdentity.getId()) ||
                                                    identityPrincipal.getAuthority().equals("ADMIN")
                                            );
                                        }
                                )
                                .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED))
                )
                .thenApplyAsync(identityMapper::toIdentityResponse)
                .thenApplyAsync(
                        identityResponse -> Optional.ofNullable(identityResponse).orElseGet(IdentityResponse::new)
                );
    }

    @Async
    public CompletableFuture<IdentityResponse> findByEmail(String email) {
        return CompletableFuture
                .supplyAsync(() -> identityRepository.findIdentityByEmail(email))
                .thenApplyAsync(identity -> identity.map(identityMapper::toIdentityResponse))
                .thenApplyAsync(
                        identityResponse -> identityResponse.orElseGet(IdentityResponse::new)
                );
    }

    @Async
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<IdentityResponse> create(IdentityRequest identityRequest) {
        return CompletableFuture
                .supplyAsync(() -> identityRepository.save(identityMapper.toIdentity(identityRequest)))
                .thenApplyAsync(identityMapper::toIdentityResponse);
    }

    private IdentityPrincipal getIdentityPrincipal(Long identityId, String authValue) {
        return Optional.ofNullable(restClientHandler.getPrincipal(authValue).getBody())
                .filter(identityPrincipal -> identityPrincipal.getAuthenticated() &&
                                             identityPrincipal.getId().equals(identityId))
                .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED));
    }

    @Async
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<IdentityResponse> update(IdentityRequest identityRequest, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getIdentityPrincipal(identityRequest.id(), authValue))
                .thenApplyAsync(_ -> identityRepository.save(identityMapper.toIdentity(identityRequest)))
                .thenApplyAsync(identityMapper::toIdentityResponse);
    }

    @Async
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<String> delete(Long identityId, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getIdentityPrincipal(identityId, authValue))
                .thenApplyAsync(
                        identityPrincipal -> identityRepository.findById(identityPrincipal.getId())
                                .orElseThrow(() -> new IdentityNotFoundException(String.format(IDENTITY_NOT_FOUND, identityId)))
                )
                .thenAcceptAsync(_ -> identityRepository.deleteById(identityId))
                .thenApplyAsync(_ -> String.format(IDENTITY_DELETED, identityId));
    }
}
