package org.burgas.appointmentservice.router;

import org.burgas.appointmentservice.handler.ConclusionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class ConclusionRouter {

    @Bean
    public RouterFunction<ServerResponse> conclusionRoutes(ConclusionHandler conclusionHandler) {
        return RouterFunctions.route()
                .GET("/conclusions/{conclusion-id}", conclusionHandler.handleFindById())
                .POST("/conclusions/create", conclusionHandler.handleCreateOrUpdate())
                .PUT("/conclusions/update", conclusionHandler.handleCreateOrUpdate())
                .build();
    }
}
