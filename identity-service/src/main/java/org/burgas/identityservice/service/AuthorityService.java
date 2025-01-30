package org.burgas.identityservice.service;

import org.burgas.identityservice.dto.AuthorityRequest;
import org.burgas.identityservice.dto.AuthorityResponse;
import org.burgas.identityservice.dto.IdentityPrincipal;
import org.burgas.identityservice.exception.AuthorityNotFoundException;
import org.burgas.identityservice.exception.NotAuthorizedException;
import org.burgas.identityservice.handler.RestClientHandler;
import org.burgas.identityservice.mapper.AuthorityMapper;
import org.burgas.identityservice.repository.AuthorityRepository;
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
public class AuthorityService {

    private static final String AUTHORITY_NOT_FOUND = "Уровня доступа с идентификатором %s не существует";
    private static final String AUTHORITY_DELETED = "Уровень доступа с идентификатором %s удален";
    public static final String NOT_AUTHORIZED = "Пользователь не аутентифицирован или не авторизован (уровень доступа \"ADMIN\")";

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;
    private final RestClientHandler restClientHandler;

    public AuthorityService(
            AuthorityRepository authorityRepository,
            AuthorityMapper authorityMapper, RestClientHandler restClientHandler
    ) {
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
        this.restClientHandler = restClientHandler;
    }

    private IdentityPrincipal getAdmin(String authValue) {
        return Optional.ofNullable(restClientHandler.getPrincipal(authValue).getBody())
                .filter(identityPrincipal -> identityPrincipal.getAuthenticated() &&
                                             identityPrincipal.getAuthority().equals("ADMIN")
                )
                .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED));
    }

    @Async
    public CompletableFuture<List<AuthorityResponse>> findAll(String authValue) {
        return CompletableFuture.supplyAsync(() -> getAdmin(authValue))
                .thenApplyAsync(_ -> authorityRepository.findAll())
                .thenApplyAsync(
                        authorities -> authorities.stream()
                                .map(authorityMapper::toAuthorityResponse)
                                .toList()
                );
    }

    @Async
    public CompletableFuture<AuthorityResponse> findById(Long authorityId, String authValue) {
        return CompletableFuture.supplyAsync(() -> getAdmin(authValue))
                .thenApplyAsync(_ -> authorityRepository.findById(authorityId))
                .thenApplyAsync(authority -> authority.map(authorityMapper::toAuthorityResponse))
                .thenApplyAsync(
                        authorityResponse ->
                                authorityResponse.orElseGet(() -> AuthorityResponse.builder().build())
                );
    }

    @Async
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<AuthorityResponse> createOrUpdate(AuthorityRequest authorityRequest, String authValue) {
        return CompletableFuture.supplyAsync(() -> getAdmin(authValue))
                .thenApplyAsync(_ -> authorityRepository.save(authorityMapper.toAuthority(authorityRequest)))
                .thenApplyAsync(authorityMapper::toAuthorityResponse);
    }

    @Async
    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<String> delete(Long authorityId, String authValue) {
        return CompletableFuture.supplyAsync(() -> getAdmin(authValue))
                .thenApplyAsync(_ -> authorityRepository.findById(authorityId)
                        .orElseThrow(() -> new AuthorityNotFoundException(String.format(AUTHORITY_NOT_FOUND, authorityId)))
                )
                .thenAcceptAsync(_ -> authorityRepository.deleteById(authorityId))
                .thenApplyAsync(_ -> String.format(AUTHORITY_DELETED, authorityId));
    }
}
