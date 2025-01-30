package org.burgas.departmentservice.handler;

import org.burgas.departmentservice.dto.DepartmentRequest;
import org.burgas.departmentservice.service.DepartmentService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class DepartmentHandler {

    private final DepartmentService departmentService;

    public DepartmentHandler(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public HandlerFunction<ServerResponse> handleFindAll() {
        return _ -> ServerResponse.ok().body(departmentService.findAll());
    }

    public HandlerFunction<ServerResponse> handleFindByFilialId() {
        return request -> ServerResponse.ok().body(
                departmentService.findByFilialId(request.pathVariable("filial-id"))
        );
    }

    public HandlerFunction<ServerResponse> handleFindById() {
        return request -> ServerResponse.ok().body(
                departmentService.findById(request.pathVariable("department-id"))
        );
    }

    public HandlerFunction<ServerResponse> handleCreateOrUpdate() {
        return request ->  ServerResponse.ok().body(
                departmentService.createOrUpdate(
                        request.body(DepartmentRequest.class), request.headers().firstHeader(AUTHORIZATION)
                )
        );
    }

    public HandlerFunction<ServerResponse> handleDelete() {
        return request -> ServerResponse.ok().body(
                departmentService.delete(
                        request.param("departmentId").orElse(null), request.headers().firstHeader(AUTHORIZATION)
                )
        );
    }
}
