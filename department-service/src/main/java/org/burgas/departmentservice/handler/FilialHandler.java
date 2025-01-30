package org.burgas.departmentservice.handler;

import org.burgas.departmentservice.dto.FilialRequest;
import org.burgas.departmentservice.service.FilialService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class FilialHandler {

    private final FilialService filialService;

    public FilialHandler(FilialService filialService) {
        this.filialService = filialService;
    }

    public HandlerFunction<ServerResponse> handleFindAll() {
        return _ -> ServerResponse.ok().body(filialService.findAll());
    }

    public HandlerFunction<ServerResponse> handleFindById() {
        return request -> ServerResponse.ok().body(
                filialService.findById(request.pathVariable("filial-id"))
        );
    }

    public HandlerFunction<ServerResponse> handleCreateOrUpdate() {
        return request -> ServerResponse.ok().body(
                filialService.createOrUpdate(
                        request.body(FilialRequest.class), request.headers().firstHeader(AUTHORIZATION)
                )
        );
    }

    public HandlerFunction<ServerResponse> handleDelete() {
        return request -> ServerResponse.ok().body(
                filialService.delete(
                        request.param("filialId").orElse(null), request.headers().firstHeader(AUTHORIZATION)
                )
        );
    }

    public HandlerFunction<ServerResponse> handleAddDepartment() {
        return request -> ServerResponse.ok().body(
                filialService.addDepartment(
                        request.param("filialId").orElse(null),
                        request.param("departmentId").orElse(null),
                        request.headers().firstHeader(AUTHORIZATION)
                )
        );
    }

    public HandlerFunction<ServerResponse> handleRemoveDepartment() {
        return request -> ServerResponse.ok().body(
                filialService.removeDepartment(
                        request.param("filialId").orElse(null),
                        request.param("departmentId").orElse(null),
                        request.headers().firstHeader(AUTHORIZATION)
                )
        );
    }
}
