package org.burgas.departmentservice.router;

import org.burgas.departmentservice.handler.FilialHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class FilialRouter {

    @Bean
    public RouterFunction<ServerResponse> filialRoutes(FilialHandler filialHandler) {
        return RouterFunctions.route()
                .GET("/filials", filialHandler.handleFindAll())
                .GET("/filials/{filial-id}", filialHandler.handleFindById())
                .POST("/filials/create", filialHandler.handleCreateOrUpdate())
                .PUT("/filials/update", filialHandler.handleCreateOrUpdate())
                .DELETE("/filials/delete", filialHandler.handleDelete())
                .POST("/filials/add-department", filialHandler.handleAddDepartment())
                .DELETE("/filials/remove-department", filialHandler.handleRemoveDepartment())
                .build();
    }
}
