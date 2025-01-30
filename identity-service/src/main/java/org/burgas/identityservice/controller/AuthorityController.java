package org.burgas.identityservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.burgas.identityservice.dto.AuthorityRequest;
import org.burgas.identityservice.dto.AuthorityResponse;
import org.burgas.identityservice.service.AuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping("/authorities")
public class AuthorityController {

    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AuthorityResponse>> getAuthorities(HttpServletRequest request)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(
                authorityService.findAll(request.getHeader(AUTHORIZATION)).get()
        );
    }

    @GetMapping(value = "/{authority-id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorityResponse> getAuthorityById(
            @PathVariable("authority-id") Long authorityId, HttpServletRequest request
    )
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(authorityService.findById(authorityId, request.getHeader(AUTHORIZATION)).get());
    }

    @PostMapping(
            value = "/create",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AuthorityResponse> addAuthority(
            @RequestBody AuthorityRequest authorityRequest, HttpServletRequest request
    )
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(authorityService.createOrUpdate(authorityRequest, request.getHeader(AUTHORIZATION)).get());
    }

    @PutMapping(
            value = "/update",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AuthorityResponse> editAuthority(
            @RequestBody AuthorityRequest authorityRequest, HttpServletRequest request
    )
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(authorityService.createOrUpdate(authorityRequest, request.getHeader(AUTHORIZATION)).get());
    }

    @DeleteMapping(
            value = "/delete",
            produces = TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> deleteAuthority(@RequestParam Long authorityId, HttpServletRequest request)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(authorityService.delete(authorityId, request.getHeader(AUTHORIZATION)).get());
    }
}
