package org.burgas.departmentservice.mapper;

import org.burgas.departmentservice.dto.FilialRequest;
import org.burgas.departmentservice.dto.FilialResponse;
import org.burgas.departmentservice.entity.Filial;
import org.burgas.departmentservice.repository.FilialRepository;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class FilialMapper {

    private final FilialRepository filialRepository;

    public FilialMapper(FilialRepository filialRepository) {
        this.filialRepository = filialRepository;
    }

    private <T> T getData(T requestData, T dbData) {
        return requestData == null ? dbData : requestData;
    }

    public Filial toFilial(FilialRequest filialRequest) {
        Long filialId = getData(filialRequest.id(), 0L);
        return filialRepository.findById(filialId)
                .map(
                        filial -> Filial.builder()
                                .id(filial.getId())
                                .address(getData(filialRequest.address(), filial.getAddress()))
                                .open(getData(filialRequest.open(), filial.getOpen()))
                                .close(getData(filialRequest.close(), filial.getClose()))
                                .build()
                )
                .orElseGet(
                        () -> Filial.builder()
                                .id(filialRequest.id())
                                .address(filialRequest.address())
                                .open(filialRequest.open())
                                .close(filialRequest.close())
                                .build()
                );
    }

    public FilialResponse toFilialResponse(Filial filial) {
        return FilialResponse.builder()
                .id(filial.getId())
                .address(filial.getAddress())
                .open(filial.getOpen().format(DateTimeFormatter.ofPattern("hh:mm")))
                .close(filial.getClose().format(DateTimeFormatter.ofPattern("hh:mm")))
                .build();
    }
}
