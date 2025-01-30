package org.burgas.identityservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.burgas.identityservice.dto.IdentityRequest;
import org.burgas.identityservice.dto.IdentityResponse;
import org.burgas.identityservice.service.IdentityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/identities")
public class IdentityController {

    private final IdentityService identityService;

    public IdentityController(IdentityService identityService) {
        this.identityService = identityService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IdentityResponse>> getIdentities(HttpServletRequest request)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(identityService.findAll(request.getHeader(AUTHORIZATION)).get());
    }

    @GetMapping(value = "/server-stream")
    public SseEmitter getIdentitiesStream(HttpServletRequest request) {
        SseEmitter sseEmitter = new SseEmitter();
        CompletableFuture.runAsync(
                () -> identityService.findAllSync(request.getHeader(AUTHORIZATION))
                        .forEach(
                                identityResponse -> {
                                    SseEmitter.SseEventBuilder data = SseEmitter.event()
                                            .id(String.valueOf(identityResponse.getId()))
                                            .name(identityResponse.getEmail())
                                            .comment(identityResponse.getAuthority().getName())
                                            .data(identityResponse, APPLICATION_JSON);
                                    try {
                                        sseEmitter.send(data);
                                        Thread.sleep(1000);

                                    } catch (IOException | InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        )
        );
        return sseEmitter;
    }

    @GetMapping(value = "/{identity-id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<IdentityResponse> getIdentityById(
            @PathVariable("identity-id") Long identityId, HttpServletRequest request
    )
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(identityService.findById(identityId, request.getHeader(AUTHORIZATION)).get());
    }

    @GetMapping(value = "/by-email/{email}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<IdentityResponse> getIdentityByEmail(@PathVariable String email)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(identityService.findByEmail(email).get());
    }

    @PostMapping(
            value = "/create",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<IdentityResponse> createIdentity(@RequestBody IdentityRequest identityRequest)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(identityService.create(identityRequest).get());
    }

    @PutMapping(
            value = "/update",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<IdentityResponse> updateIdentity(@RequestBody IdentityRequest identityRequest, HttpServletRequest request)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(identityService.update(identityRequest, request.getHeader(AUTHORIZATION)).get());
    }

    @DeleteMapping(value = "/delete", produces = TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteIdentity(@RequestParam Long identityId, HttpServletRequest request)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(identityService.delete(identityId, request.getHeader(AUTHORIZATION)).get());
    }
}
