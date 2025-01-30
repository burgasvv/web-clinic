package org.burgas.appointmentservice.service;

import jakarta.servlet.http.Part;
import org.burgas.appointmentservice.dto.AppointmentResponse;
import org.burgas.appointmentservice.entity.Document;
import org.burgas.appointmentservice.exception.DocumentNotFoundException;
import org.burgas.appointmentservice.exception.WrongAuthorizationException;
import org.burgas.appointmentservice.handler.RestClientHandler;
import org.burgas.appointmentservice.repository.DocumentRepository;
import org.burgas.identityservice.dto.IdentityPrincipal;
import org.burgas.identityservice.exception.NotAuthorizedException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class DocumentService {

    public static final String NOT_AUTHORIZED = "Пользователь не аутентифицирован или не авторизован";
    public static final String WRONG_AUTHORIZATION = "Неверная авторизация для выполнения загрузки файла";
    public static final String FILE_UPLOADED = "Файл успешно загружен";

    private final DocumentRepository documentRepository;
    private final RestClientHandler restClientHandler;
    private final AppointmentService appointmentService;

    public DocumentService(
            DocumentRepository documentRepository,
            RestClientHandler restClientHandler, AppointmentService appointmentService
    ) {
        this.documentRepository = documentRepository;
        this.restClientHandler = restClientHandler;
        this.appointmentService = appointmentService;
    }

    private IdentityPrincipal getAuthorizedPrincipal(String authValue) {
        return Optional.ofNullable(restClientHandler.getPrincipal(authValue).getBody())
                .filter(IdentityPrincipal::getAuthenticated)
                .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED));
    }

    @Async
    public CompletableFuture<Document> findById(String documentId, String authValue) {
        return CompletableFuture
                .supplyAsync(() -> getAuthorizedPrincipal(authValue))
                .thenApplyAsync(_ -> documentRepository.findById(Long.valueOf(documentId))
                                .orElseThrow(() -> new DocumentNotFoundException("Искомый документ не найден"))
                );
    }

    @Async
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public CompletableFuture<String> uploadSingleFile(Part pdfFile, String appointmentId, String authValue) {
        return CompletableFuture
                .supplyAsync(
                        () -> Optional.ofNullable(restClientHandler.getPrincipal(authValue).getBody())
                                .filter(identityPrincipal -> identityPrincipal.getAuthenticated() &&
                                                             identityPrincipal.getAuthority().equals("EMPLOYEE")
                                )
                                .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED))
                )
                .thenApplyAsync(identityPrincipal -> {
                            try {
                                AppointmentResponse appointmentResponse = appointmentService.findById(appointmentId, authValue).get();
                                if (identityPrincipal.getId().equals(appointmentResponse.getEmployee().getIdentityId())) {
                                    documentRepository.save(
                                            Document.builder()
                                                    .filename(pdfFile.getSubmittedFileName())
                                                    .data(pdfFile.getInputStream().readAllBytes())
                                                    .build()
                                    );
                                    return FILE_UPLOADED;

                                } else
                                    throw new WrongAuthorizationException(WRONG_AUTHORIZATION);

                            } catch (InterruptedException | ExecutionException | IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
    }
}
