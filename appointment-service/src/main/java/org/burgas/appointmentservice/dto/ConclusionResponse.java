package org.burgas.appointmentservice.dto;

import org.burgas.appointmentservice.entity.Document;

public class ConclusionResponse {

    private Long id;
    private AppointmentResponse appointment;
    private Document document;

    public ConclusionResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public AppointmentResponse getAppointment() {
        return appointment;
    }

    @SuppressWarnings("unused")
    public void setAppointment(AppointmentResponse appointment) {
        this.appointment = appointment;
    }

    @SuppressWarnings("unused")
    public Document getDocument() {
        return document;
    }

    @SuppressWarnings("unused")
    public void setDocument(Document document) {
        this.document = document;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final ConclusionResponse conclusion;

        public Builder() {
            conclusion = new ConclusionResponse();
        }

        public Builder id(Long id) {
            this.conclusion.id = id;
            return this;
        }

        public Builder appointment(AppointmentResponse appointment) {
            this.conclusion.appointment = appointment;
            return this;
        }

        public Builder document(Document document) {
            this.conclusion.document = document;
            return this;
        }

        public ConclusionResponse build() {
            return conclusion;
        }
    }
}
