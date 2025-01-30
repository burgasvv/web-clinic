package org.burgas.appointmentservice.handler;

import org.burgas.appointmentservice.dto.ConclusionRequest;
import org.burgas.appointmentservice.service.ConclusionService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class ConclusionHandler {

    private final ConclusionService conclusionService;

    public ConclusionHandler(ConclusionService conclusionService) {
        this.conclusionService = conclusionService;
    }

    public HandlerFunction<ServerResponse> handleFindById() {
        return request -> ServerResponse.ok().body(
                conclusionService.findById(
                        request.pathVariable("conclusion-id"), request.headers().firstHeader(AUTHORIZATION)
                )
        );
    }

    public HandlerFunction<ServerResponse> handleCreateOrUpdate() {
        return request -> ServerResponse.ok().body(
                conclusionService.createOrUpdate(request.bind(ConclusionRequest.class), request.headers().firstHeader(AUTHORIZATION))
        );
    }
}
