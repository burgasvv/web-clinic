package org.burgas.appointmentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Analysis {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long patientId;
    private Long employeeId;
    private Long documentId;

    public Analysis() {
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
    public Long getPatientId() {
        return patientId;
    }

    @SuppressWarnings("unused")
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @SuppressWarnings("unused")
    public Long getEmployeeId() {
        return employeeId;
    }

    @SuppressWarnings("unused")
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @SuppressWarnings("unused")
    public Long getDocumentId() {
        return documentId;
    }

    @SuppressWarnings("unused")
    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Analysis analysis;

        public Builder() {
            analysis = new Analysis();
        }

        public Builder id(Long id) {
            this.analysis.id = id;
            return this;
        }

        public Builder patientId(Long patientId) {
            this.analysis.patientId = patientId;
            return this;
        }

        public Builder employeeId(Long employeeId) {
            this.analysis.employeeId = employeeId;
            return this;
        }

        public Builder documentId(Long documentId) {
            this.analysis.documentId = documentId;
            return this;
        }

        public Analysis build() {
            return analysis;
        }
    }
}
