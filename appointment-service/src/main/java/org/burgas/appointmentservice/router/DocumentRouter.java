package org.burgas.appointmentservice.router;

import org.burgas.appointmentservice.handler.DocumentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class DocumentRouter {

    @Bean
    public RouterFunction<ServerResponse> documentRoutes(DocumentHandler documentHandler) {
        return RouterFunctions.route()
                .GET("/documents/{document-id}", documentHandler.handleFindById())
                .GET("/documents/audio/{document-id}", documentHandler.handleFindByIdAudio())
                .GET("/documents/video/{document-id}", documentHandler.handleFindByIdVideo())
                .POST("/documents/upload-single-audio-file/from-appointment/{appointment-id}", documentHandler.handleUploadSingleFile())
                .POST("/documents/upload-single-video-file/from-appointment/{appointment-id}", documentHandler.handleUploadSingleFile())
                .POST("/documents/upload-single-pdf-file/from-appointment/{appointment-id}", documentHandler.handleUploadSingleFile())
                .build();
    }
}
