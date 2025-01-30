package org.burgas.employeeservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.burgas.employeeservice.dto.PositionRequest;
import org.burgas.employeeservice.dto.PositionResponse;
import org.burgas.employeeservice.service.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<PositionResponse>> getPositions()
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(positionService.findAll().get());
    }

    @GetMapping(value = "/{position-id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<PositionResponse> getPositionById(@PathVariable("position-id") Long positionId)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(positionService.findById(positionId).get());
    }

    @PostMapping(value = "/create", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<PositionResponse> createPosition(
            @RequestBody PositionRequest positionRequest, HttpServletRequest request
    ) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(
                positionService.createOrUpdate(positionRequest, request.getHeader(AUTHORIZATION)).get()
        );
    }

    @PutMapping(value = "/update", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<PositionResponse> updatePosition(
            @RequestBody PositionRequest positionRequest, HttpServletRequest request
    ) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(
                positionService.createOrUpdate(positionRequest, request.getHeader(AUTHORIZATION)).get()
        );
    }
}
