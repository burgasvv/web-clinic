package org.burgas.gatewayserver.config;

import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class ProxyConfig {

    public static final String IDENTITY_SERVICE_HANDLER_URI = "http://localhost:8888";
    public static final String DEPARTMENT_SERVICE_HANDLER_URI = "http://localhost:8090";
    public static final String EMPLOYEE_SERVICE_HANDLER_URI = "http://localhost:9000";
    public static final String PATIENT_SERVICE_HANDLER_URI = "http://localhost:9010";
    public static final String APPOINTMENT_SERVICE_HANDLER_URI = "http://localhost:9020";

    @Bean
    public RouterFunction<ServerResponse> routeConfig() {
        return RouterFunctions.route()

                .GET("/authorities/**", HandlerFunctions.http(IDENTITY_SERVICE_HANDLER_URI))
                .POST("/authorities/**", HandlerFunctions.http(IDENTITY_SERVICE_HANDLER_URI))
                .PUT("/authorities/**", HandlerFunctions.http(IDENTITY_SERVICE_HANDLER_URI))
                .DELETE("/authorities/**", HandlerFunctions.http(IDENTITY_SERVICE_HANDLER_URI))
                .GET("/identities/**", HandlerFunctions.http(IDENTITY_SERVICE_HANDLER_URI))
                .POST("/identities/**", HandlerFunctions.http(IDENTITY_SERVICE_HANDLER_URI))
                .PUT("/identities/**", HandlerFunctions.http(IDENTITY_SERVICE_HANDLER_URI))

                .GET("/filials/**", HandlerFunctions.http(DEPARTMENT_SERVICE_HANDLER_URI))
                .POST("/filials/**", HandlerFunctions.http(DEPARTMENT_SERVICE_HANDLER_URI))
                .PUT("/filials/**", HandlerFunctions.http(DEPARTMENT_SERVICE_HANDLER_URI))
                .DELETE("/filials/**", HandlerFunctions.http(DEPARTMENT_SERVICE_HANDLER_URI))
                .GET("/departments/**", HandlerFunctions.http(DEPARTMENT_SERVICE_HANDLER_URI))
                .POST("/departments/**", HandlerFunctions.http(DEPARTMENT_SERVICE_HANDLER_URI))
                .PUT("/departments/**", HandlerFunctions.http(DEPARTMENT_SERVICE_HANDLER_URI))
                .DELETE("/departments/**", HandlerFunctions.http(DEPARTMENT_SERVICE_HANDLER_URI))

                .GET("/positions/**", HandlerFunctions.http(EMPLOYEE_SERVICE_HANDLER_URI))
                .POST("/positions/**", HandlerFunctions.http(EMPLOYEE_SERVICE_HANDLER_URI))
                .PUT("/positions/**", HandlerFunctions.http(EMPLOYEE_SERVICE_HANDLER_URI))
                .DELETE("/positions/**", HandlerFunctions.http(EMPLOYEE_SERVICE_HANDLER_URI))
                .GET("/employees/**", HandlerFunctions.http(EMPLOYEE_SERVICE_HANDLER_URI))
                .POST("/employees/**", HandlerFunctions.http(EMPLOYEE_SERVICE_HANDLER_URI))
                .PUT("/employees/**", HandlerFunctions.http(EMPLOYEE_SERVICE_HANDLER_URI))
                .DELETE("/employees/**", HandlerFunctions.http(EMPLOYEE_SERVICE_HANDLER_URI))

                .GET("/patients/**", HandlerFunctions.http(PATIENT_SERVICE_HANDLER_URI))
                .POST("/patients/**", HandlerFunctions.http(PATIENT_SERVICE_HANDLER_URI))
                .PUT("/patients/**", HandlerFunctions.http(PATIENT_SERVICE_HANDLER_URI))
                .DELETE("/patients/**", HandlerFunctions.http(PATIENT_SERVICE_HANDLER_URI))

                .GET("/appointments/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .POST("/appointments/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .PUT("/appointments/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .DELETE("/appointments/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .GET("/conclusions/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .POST("/conclusions/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .PUT("/conclusions/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .DELETE("/conclusions/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .GET("/analysis/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .POST("/analysis/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .PUT("/analysis/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .DELETE("/analysis/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .GET("/documents/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .POST("/documents/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .PUT("/documents/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))
                .DELETE("/documents/**", HandlerFunctions.http(APPOINTMENT_SERVICE_HANDLER_URI))

                .build();
    }
}
