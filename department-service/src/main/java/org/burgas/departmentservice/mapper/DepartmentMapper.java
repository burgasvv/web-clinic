package org.burgas.departmentservice.mapper;

import org.burgas.departmentservice.dto.DepartmentRequest;
import org.burgas.departmentservice.dto.DepartmentResponse;
import org.burgas.departmentservice.entity.Department;
import org.burgas.departmentservice.repository.DepartmentRepository;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    private final DepartmentRepository departmentRepository;

    public DepartmentMapper(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    private <T> T getData(T requestData, T dbData) {
        return requestData == null ? dbData : requestData;
    }

    public Department toDepartment(DepartmentRequest departmentRequest) {
        Long departmentId = getData(departmentRequest.id(), 0L);
        return departmentRepository.findById(departmentId)
                .map(
                        department -> Department.builder()
                                .id(department.getId())
                                .name(getData(departmentRequest.name(), department.getName()))
                                .description(getData(departmentRequest.description(), department.getDescription()))
                                .build()
                )
                .orElseGet(
                        () -> Department.builder()
                                .id(departmentRequest.id())
                                .name(departmentRequest.name())
                                .description(departmentRequest.description())
                                .build()
                );
    }

    public DepartmentResponse toDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .build();
    }
}
