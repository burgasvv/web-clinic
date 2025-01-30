package org.burgas.employeeservice.mapper;

import org.burgas.employeeservice.dto.EmployeeRequest;
import org.burgas.employeeservice.dto.EmployeeResponse;
import org.burgas.employeeservice.entity.Employee;
import org.burgas.employeeservice.handler.RestClientHandler;
import org.burgas.employeeservice.repository.EmployeeRepository;
import org.burgas.employeeservice.repository.PositionRepository;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class EmployeeMapper {

    private final EmployeeRepository employeeRepository;
    private final RestClientHandler restClientHandler;
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    public EmployeeMapper(
            EmployeeRepository employeeRepository, RestClientHandler restClientHandler,
            PositionRepository positionRepository, PositionMapper positionMapper
    ) {
        this.employeeRepository = employeeRepository;
        this.restClientHandler = restClientHandler;
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
    }

    private <T> T getData(T requestData, T dbData) {
        return requestData == null ? dbData : requestData;
    }

    public Employee toEmployee(EmployeeRequest employeeRequest) {
        Long employeeId = getData(employeeRequest.id(), 0L);
        return employeeRepository.findById(employeeId)
                .map(
                        employee -> Employee.builder()
                                .id(employee.getId())
                                .firstname(getData(employeeRequest.firstname(), employee.getFirstname()))
                                .lastname(getData(employeeRequest.lastname(), employee.getLastname()))
                                .patronymic(getData(employeeRequest.patronymic(), employee.getPatronymic()))
                                .birthday(getData(employeeRequest.birthdate(), employee.getBirthdate()))
                                .about(getData(employeeRequest.about(), employee.getAbout()))
                                .gender(getData(employeeRequest.gender(), employee.getGender()))
                                .identityId(getData(employeeRequest.identityId(), employee.getIdentityId()))
                                .filialId(getData(employeeRequest.filialId(), employee.getFilialId()))
                                .departmentId(getData(employeeRequest.departmentId(), employee.getDepartmentId()))
                                .build()
                )
                .orElseGet(
                        () -> Employee.builder()
                                .id(employeeRequest.id())
                                .firstname(employeeRequest.firstname())
                                .lastname(employeeRequest.lastname())
                                .patronymic(employeeRequest.patronymic())
                                .gender(employeeRequest.gender())
                                .about(employeeRequest.about())
                                .birthday(employeeRequest.birthdate())
                                .identityId(employeeRequest.identityId())
                                .filialId(employeeRequest.filialId())
                                .departmentId(employeeRequest.departmentId())
                                .build()
                );
    }

    public EmployeeResponse toEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstname(employee.getFirstname())
                .lastname(employee.getLastname())
                .patronymic(employee.getPatronymic())
                .about(employee.getAbout())
                .gender(employee.getGender().getName())
                .birthdate(employee.getBirthdate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")))
                .identityId(employee.getIdentityId())
                .filial(restClientHandler.getFilialById(employee.getFilialId()).getBody())
                .department(restClientHandler.getDepartmentById(employee.getDepartmentId()).getBody())
                .positions(
                        positionRepository.findPositionsByEmployeeId(employee.getId())
                                .stream().map(positionMapper::toPositionResponse).toList()
                )
                .build();
    }
}
