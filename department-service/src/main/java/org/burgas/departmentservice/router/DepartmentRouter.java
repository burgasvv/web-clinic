package org.burgas.departmentservice.router;

import org.burgas.departmentservice.handler.DepartmentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.*;

@Configuration
public class DepartmentRouter {

    @Bean
    public RouterFunction<ServerResponse> departmentRoutes(DepartmentHandler departmentHandler) {
        return RouterFunctions
                .route(GET("/departments"), departmentHandler.handleFindAll())
                .andRoute(GET("/departments/{department-id}"), departmentHandler.handleFindById())
                .andRoute(POST("/departments/create"), departmentHandler.handleCreateOrUpdate())
                .andRoute(PUT("/departments/update"), departmentHandler.handleCreateOrUpdate())
                .andRoute(DELETE("/departments/delete"), departmentHandler.handleDelete())
                .andRoute(GET("/departments/in-filial/{filial-id}"), departmentHandler.handleFindByFilialId());
    }
}
