package org.burgas.patientservice.mapper;

import org.burgas.patientservice.dto.PatientRequest;
import org.burgas.patientservice.dto.PatientResponse;
import org.burgas.patientservice.entity.Patient;
import org.burgas.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class PatientMapper {

    private final PatientRepository patientRepository;

    public PatientMapper(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    private <T> T getData(T requestData, T dbData) {
        return requestData == null ? dbData : requestData;
    }

    public Patient toPatient(PatientRequest patientRequest) {
        Long patientId = getData(patientRequest.id(), 0L);
        return patientRepository.findById(patientId)
                .map(
                        patient -> Patient.builder()
                                .id(patient.getId())
                                .firstname(getData(patientRequest.firstname(), patient.getFirstname()))
                                .lastname(getData(patientRequest.lastname(), patient.getLastname()))
                                .patronymic(getData(patientRequest.patronymic(), patient.getPatronymic()))
                                .gender(getData(patientRequest.gender(), patient.getGender()))
                                .birthdate(getData(patientRequest.birthdate(), patient.getBirthdate()))
                                .identityId(getData(patientRequest.identityId(), patient.getIdentityId()))
                                .build()
                )
                .orElseGet(
                        () -> Patient.builder()
                                .id(patientRequest.id())
                                .firstname(patientRequest.firstname())
                                .lastname(patientRequest.lastname())
                                .patronymic(patientRequest.patronymic())
                                .birthdate(patientRequest.birthdate())
                                .gender(patientRequest.gender())
                                .identityId(patientRequest.identityId())
                                .build()
                );
    }

    public PatientResponse toPatientResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .firstname(patient.getFirstname())
                .lastname(patient.getLastname())
                .patronymic(patient.getPatronymic())
                .birthdate(
                        patient.getBirthdate().format(
                                DateTimeFormatter.ofPattern("dd MMMM yyyy")
                        )
                )
                .identityId(patient.getIdentityId())
                .gender(patient.getGender().getName())
                .build();
    }
}
