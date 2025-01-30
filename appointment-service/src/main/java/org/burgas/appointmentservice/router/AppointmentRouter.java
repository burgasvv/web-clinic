package org.burgas.appointmentservice.router;

import org.burgas.appointmentservice.handler.AppointmentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class AppointmentRouter {

    @Bean
    public RouterFunction<ServerResponse> appointmentRoutes(AppointmentHandler appointmentHandler) {
        return RouterFunctions.route()
                .GET("/appointments", appointmentHandler.handleFindAll())
                .GET("/appointments/{appointment-id}", appointmentHandler.handleFindById())
                .GET("/appointments/by-patient/get", appointmentHandler.handleFindByPatientId())
                .GET("/appointments/by-employee/get", appointmentHandler.handleFindByEmployeeId())
                .POST("/appointments/create-by-patient", appointmentHandler.handleCreateOrUpdateByPatient())
                .PUT("/appointments/update-by-patient", appointmentHandler.handleCreateOrUpdateByPatient())
                .build();
    }
}
