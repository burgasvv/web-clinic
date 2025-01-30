package org.burgas.appointmentservice.mapper;

import org.burgas.appointmentservice.dto.AppointmentResponse;
import org.burgas.appointmentservice.dto.ConclusionRequest;
import org.burgas.appointmentservice.dto.ConclusionResponse;
import org.burgas.appointmentservice.entity.Conclusion;
import org.burgas.appointmentservice.entity.Document;
import org.burgas.appointmentservice.repository.AppointmentRepository;
import org.burgas.appointmentservice.repository.ConclusionRepository;
import org.burgas.appointmentservice.repository.DocumentRepository;
import org.springframework.stereotype.Component;

@Component
public class ConclusionMapper {

    private final ConclusionRepository conclusionRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final DocumentRepository documentRepository;

    public ConclusionMapper(
            ConclusionRepository conclusionRepository, AppointmentRepository appointmentRepository,
            AppointmentMapper appointmentMapper, DocumentRepository documentRepository
    ) {
        this.conclusionRepository = conclusionRepository;
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.documentRepository = documentRepository;
    }

    private <T> T getData(T requestData, T dbData) {
        return requestData == null ? dbData : requestData;
    }

    public Conclusion toConclusion(ConclusionRequest conclusionRequest) {
        Long conclusionId = getData(conclusionRequest.id(), 0L);
        return conclusionRepository.findById(conclusionId)
                .map(
                        conclusion -> Conclusion.builder()
                                .id(conclusion.getId())
                                .appointmentId(getData(conclusionRequest.appointmentId(), conclusion.getAppointmentId()))
                                .documentId(getData(conclusionRequest.documentId(), conclusion.getDocumentId()))
                                .build()
                )
                .orElseGet(
                        () -> Conclusion.builder()
                                .id(conclusionRequest.id())
                                .appointmentId(conclusionRequest.appointmentId())
                                .documentId(conclusionRequest.documentId())
                                .build()
                );
    }

    public ConclusionResponse toConclusionResponse(Conclusion conclusion, String authValue) {
        return ConclusionResponse.builder()
                .id(conclusion.getId())
                .appointment(
                        appointmentRepository.findById(conclusion.getAppointmentId())
                                .map(appointment -> appointmentMapper.toAppointmentResponse(appointment, authValue)
                                )
                                .orElseGet(() -> AppointmentResponse.builder().build())
                )
                .document(
                        documentRepository.findById(conclusion.getDocumentId())
                                .orElseGet(() -> Document.builder().build())
                )
                .build();
    }
}
