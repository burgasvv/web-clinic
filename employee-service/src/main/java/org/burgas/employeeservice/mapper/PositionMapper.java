package org.burgas.employeeservice.mapper;

import org.burgas.employeeservice.dto.PositionRequest;
import org.burgas.employeeservice.dto.PositionResponse;
import org.burgas.employeeservice.entity.Position;
import org.burgas.employeeservice.repository.PositionRepository;
import org.springframework.stereotype.Component;

@Component
public class PositionMapper {

    private final PositionRepository positionRepository;

    public PositionMapper(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    private <T> T getData(T requestData, T dbData) {
        return requestData == null ? dbData : requestData;
    }

    public Position toPosition(PositionRequest positionRequest) {
        Long positionId = getData(positionRequest.id(), 0L);
        return positionRepository.findById(positionId)
                .map(
                        position -> Position.builder()
                                .id(position.getId())
                                .name(getData(positionRequest.name(), position.getName()))
                                .build()
                )
                .orElseGet(
                        () -> Position.builder()
                                .id(positionRequest.id())
                                .name(positionRequest.name())
                                .build()
                );
    }

    public PositionResponse toPositionResponse(Position position) {
        return PositionResponse.builder()
                .id(position.getId())
                .name(position.getName())
                .build();
    }
}
