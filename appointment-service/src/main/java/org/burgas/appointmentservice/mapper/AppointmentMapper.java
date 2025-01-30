package org.burgas.appointmentservice.mapper;

import org.burgas.appointmentservice.dto.AppointmentRequest;
import org.burgas.appointmentservice.dto.AppointmentResponse;
import org.burgas.appointmentservice.entity.Appointment;
import org.burgas.appointmentservice.handler.RestClientHandler;
import org.burgas.appointmentservice.repository.AppointmentRepository;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class AppointmentMapper {

    private final AppointmentRepository appointmentRepository;
    private final RestClientHandler restClientHandler;

    public AppointmentMapper(AppointmentRepository appointmentRepository, RestClientHandler restClientHandler) {
        this.appointmentRepository = appointmentRepository;
        this.restClientHandler = restClientHandler;
    }

    public <T> T getData(T requestData, T dbData) {
        return requestData == null ? dbData : requestData;
    }

    public Appointment toAppointment(AppointmentRequest appointmentRequest) {
        Long appointmentId = appointmentRequest.id() == null ? 0L : appointmentRequest.id();
        return appointmentRepository.findById(appointmentId)
                .map(
                        appointment -> Appointment.builder()
                                .id(appointment.getId())
                                .firstname(getData(appointmentRequest.firstname(), appointment.getFirstname()))
                                .lastname(getData(appointmentRequest.lastname(), appointment.getLastname()))
                                .patronymic(getData(appointmentRequest.patronymic(), appointment.getPatronymic()))
                                .description(getData(appointmentRequest.description(), appointment.getDescription()))
                                .gender(getData(appointmentRequest.gender(), appointment.getGender()))
                                .date(getData(appointmentRequest.date(), appointment.getDate()))
                                .hours(getData(appointmentRequest.hours(), appointment.getHours()))
                                .employeeId(getData(appointmentRequest.employeeId(), appointment.getEmployeeId()))
                                .patientId(getData(appointmentRequest.patientId(), appointment.getPatientId()))
                                .closed(getData(appointmentRequest.closed(), appointment.getClosed()))
                                .build()
                )
                .orElseGet(
                        () -> Appointment.builder()
                                .id(appointmentRequest.id())
                                .firstname(appointmentRequest.firstname())
                                .lastname(appointmentRequest.lastname())
                                .patronymic(appointmentRequest.patronymic())
                                .gender(appointmentRequest.gender())
                                .description(appointmentRequest.description())
                                .date(appointmentRequest.date())
                                .hours(appointmentRequest.hours())
                                .patientId(appointmentRequest.patientId())
                                .employeeId(appointmentRequest.employeeId())
                                .closed(appointmentRequest.closed())
                                .build()
                );
    }

    public AppointmentResponse toAppointmentResponse(Appointment appointment, String authValue) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .firstname(appointment.getFirstname())
                .lastname(appointment.getLastname())
                .patronymic(appointment.getPatronymic())
                .description(appointment.getDescription())
                .gender(appointment.getGender().getName())
                .hours(appointment.getHours().format(DateTimeFormatter.ofPattern("hh:mm")))
                .date(appointment.getDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")))
                .employee(restClientHandler.getEmployeeById(appointment.getEmployeeId()).getBody())
                .patient(restClientHandler.getPatientById(appointment.getPatientId(), authValue).getBody())
                .closed(appointment.getClosed())
                .build();
    }
}
