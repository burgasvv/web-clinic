package org.burgas.appointmentservice.handler;

import org.burgas.appointmentservice.entity.Document;
import org.burgas.appointmentservice.service.DocumentService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.ByteArrayInputStream;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_PDF;

@Component
public class DocumentHandler {

    private final DocumentService documentService;

    public DocumentHandler(DocumentService documentService) {
        this.documentService = documentService;
    }

    public HandlerFunction<ServerResponse> handleFindById() {
        return request -> {
            Document document = documentService.findById(request.pathVariable("document-id"),
                    request.headers().firstHeader(AUTHORIZATION)).get();
            return ServerResponse.ok()
                    .contentType(APPLICATION_PDF)
                    .body(new InputStreamResource(
                                    new ByteArrayInputStream(document.getData()))
                    );
        };
    }

    public HandlerFunction<ServerResponse> handleFindByIdAudio() {
        return request -> {
            Document document = documentService.findById(request.pathVariable("document-id"),
                    request.headers().firstHeader(AUTHORIZATION)).get();
            return ServerResponse.ok()
                    .contentType(MediaType.valueOf("audio/mp3"))
                    .body(new InputStreamResource(
                                    new ByteArrayInputStream(document.getData()))
                    );
        };
    }

    public HandlerFunction<ServerResponse> handleFindByIdVideo() {
        return request -> {
            Document document = documentService.findById(request.pathVariable("document-id"),
                    request.headers().firstHeader(AUTHORIZATION)).get();
            return ServerResponse.ok()
                    .contentType(MediaType.valueOf("video/mp4"))
                    .body(new InputStreamResource(
                            new ByteArrayInputStream(document.getData()))
                    );
        };
    }

    public HandlerFunction<ServerResponse> handleUploadSingleFile() {
        return request -> ServerResponse.ok().body(
                documentService.uploadSingleFile(
                        request.multipartData().getFirst("file"),
                        request.pathVariable("appointment-id"),
                        request.headers().firstHeader(AUTHORIZATION))
        );
    }
}
