package org.burgas.employeeservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.burgas.employeeservice.dto.EmployeeRequest;
import org.burgas.employeeservice.dto.EmployeeResponse;
import org.burgas.employeeservice.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<EmployeeResponse>> getEmployees()
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(employeeService.findAll().get());
    }

    @GetMapping(value = "/{employee-id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable("employee-id") Long employeeId)
            throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(employeeService.findById(employeeId).get());
    }

    @PostMapping(value = "/create", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<EmployeeResponse> createEmployee(
            @RequestBody EmployeeRequest employeeRequest, HttpServletRequest request
    ) {
        return ResponseEntity.ok(employeeService.createOrUpdate(employeeRequest, request.getHeader(AUTHORIZATION)));
    }

    @PutMapping(value = "/update", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<EmployeeResponse> updateEmployee(
            @RequestBody EmployeeRequest employeeRequest, HttpServletRequest request
    ) {
        return ResponseEntity.ok(employeeService.createOrUpdate(employeeRequest, request.getHeader(AUTHORIZATION)));
    }

    @DeleteMapping(value = "/delete")
    public @ResponseBody ResponseEntity<String> deleteEmployee(@RequestParam Long employeeId, HttpServletRequest request) {
        return ResponseEntity.ok(employeeService.delete(employeeId, request.getHeader(AUTHORIZATION)));
    }
}
