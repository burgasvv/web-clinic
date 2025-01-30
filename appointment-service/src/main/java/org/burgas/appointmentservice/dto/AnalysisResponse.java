package org.burgas.appointmentservice.dto;

import org.burgas.appointmentservice.entity.Document;
import org.burgas.employeeservice.dto.EmployeeResponse;
import org.burgas.patientservice.dto.PatientResponse;

public class AnalysisResponse {

    private Long id;
    private PatientResponse patient;
    private EmployeeResponse employee;
    private Document document;

    public AnalysisResponse() {
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public PatientResponse getPatient() {
        return patient;
    }

    @SuppressWarnings("unused")
    public void setPatient(PatientResponse patient) {
        this.patient = patient;
    }

    @SuppressWarnings("unused")
    public EmployeeResponse getEmployee() {
        return employee;
    }

    @SuppressWarnings("unused")
    public void setEmployee(EmployeeResponse employee) {
        this.employee = employee;
    }

    @SuppressWarnings("unused")
    public Document getDocumentId() {
        return document;
    }

    @SuppressWarnings("unused")
    public void setDocumentId(Document document) {
        this.document = document;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final AnalysisResponse analysis;

        public Builder() {
            analysis = new AnalysisResponse();
        }

        public Builder id(Long id) {
            this.analysis.id = id;
            return this;
        }

        public Builder patient(PatientResponse patient) {
            this.analysis.patient = patient;
            return this;
        }

        public Builder employee(EmployeeResponse employee) {
            this.analysis.employee = employee;
            return this;
        }

        public Builder documentId(Document document) {
            this.analysis.document = document;
            return this;
        }

        public AnalysisResponse build() {
            return analysis;
        }
    }
}
