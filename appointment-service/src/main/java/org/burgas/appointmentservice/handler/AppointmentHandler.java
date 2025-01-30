package org.burgas.appointmentservice.handler;

import org.burgas.appointmentservice.dto.AppointmentRequest;
import org.burgas.appointmentservice.service.AppointmentService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class AppointmentHandler {

    private final AppointmentService appointmentService;

    public AppointmentHandler(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public HandlerFunction<ServerResponse> handleFindAll() {
        return request -> ServerResponse.ok().body(
                appointmentService.findAll(request.headers().firstHeader(AUTHORIZATION))
        );
    }

    public HandlerFunction<ServerResponse> handleFindById() {
        return request -> ServerResponse.ok().body(
                appointmentService.findById(
                        request.pathVariable("appointment-id"), request.headers().firstHeader(AUTHORIZATION)
                )
        );
    }

    public HandlerFunction<ServerResponse> handleFindByPatientId() {
        return request -> ServerResponse.ok().body(
                appointmentService.findByPatientId(
                        request.param("patientId").orElse(null), request.headers().firstHeader(AUTHORIZATION)
                )
        );
    }

    public HandlerFunction<ServerResponse> handleFindByEmployeeId() {
        return request -> ServerResponse.ok().body(
                appointmentService.findByEmployeeId(
                        request.param("employeeId").orElse(null), request.headers().firstHeader(AUTHORIZATION)
                )
        );
    }

    public HandlerFunction<ServerResponse> handleCreateOrUpdateByPatient() {
        return request -> ServerResponse.ok().body(
                appointmentService.createOrUpdateByPatient(
                        request.body(AppointmentRequest.class), request.headers().firstHeader(AUTHORIZATION)
                )
        );
    }
}
