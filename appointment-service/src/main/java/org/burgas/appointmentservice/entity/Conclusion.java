package org.burgas.appointmentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Conclusion {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long appointmentId;
    private Long documentId;

    public Conclusion() {
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
    public Long getAppointmentId() {
        return appointmentId;
    }

    @SuppressWarnings("unused")
    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
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

        private final Conclusion conclusion;

        public Builder() {
            conclusion = new Conclusion();
        }

        public Builder id(Long id) {
            this.conclusion.id = id;
            return this;
        }

        public Builder appointmentId(Long appointmentId) {
            this.conclusion.appointmentId = appointmentId;
            return this;
        }

        public Builder documentId(Long documentId) {
            this.conclusion.documentId = documentId;
            return this;
        }

        public Conclusion build() {
            return conclusion;
        }
    }
}
