package org.burgas.appointmentservice.dto;

import org.burgas.employeeservice.dto.EmployeeResponse;
import org.burgas.patientservice.dto.PatientResponse;

public class AppointmentResponse {

    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String gender;
    private String description;
    private String date;
    private String hours;
    private EmployeeResponse employee;
    private PatientResponse patient;
    private Boolean closed;

    public AppointmentResponse() {
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
    public String getFirstname() {
        return firstname;
    }

    @SuppressWarnings("unused")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @SuppressWarnings("unused")
    public String getLastname() {
        return lastname;
    }

    @SuppressWarnings("unused")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @SuppressWarnings("unused")
    public String getPatronymic() {
        return patronymic;
    }

    @SuppressWarnings("unused")
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @SuppressWarnings("unused")
    public String getGender() {
        return gender;
    }

    @SuppressWarnings("unused")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public void setDescription(String description) {
        this.description = description;
    }

    @SuppressWarnings("unused")
    public String getDate() {
        return date;
    }

    @SuppressWarnings("unused")
    public void setDate(String date) {
        this.date = date;
    }

    @SuppressWarnings("unused")
    public String getHours() {
        return hours;
    }

    @SuppressWarnings("unused")
    public void setHours(String hours) {
        this.hours = hours;
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
    public PatientResponse getPatient() {
        return patient;
    }

    @SuppressWarnings("unused")
    public void setPatient(PatientResponse patient) {
        this.patient = patient;
    }

    @SuppressWarnings("unused")
    public Boolean getClosed() {
        return closed;
    }

    @SuppressWarnings("unused")
    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final AppointmentResponse appointment;

        public Builder() {
            appointment = new AppointmentResponse();
        }

        public Builder id(Long id) {
            this.appointment.id = id;
            return this;
        }

        public Builder firstname(String firstname) {
            this.appointment.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.appointment.lastname = lastname;
            return this;
        }

        public Builder patronymic(String patronymic) {
            this.appointment.patronymic = patronymic;
            return this;
        }

        public Builder gender(String gender) {
            this.appointment.gender = gender;
            return this;
        }

        public Builder description(String description) {
            this.appointment.description = description;
            return this;
        }

        public Builder date(String date) {
            this.appointment.date = date;
            return this;
        }

        public Builder hours(String hours) {
            this.appointment.hours = hours;
            return this;
        }

        public Builder employee(EmployeeResponse employee) {
            this.appointment.employee = employee;
            return this;
        }

        public Builder patient(PatientResponse patient) {
            this.appointment.patient = patient;
            return this;
        }

        public Builder closed(Boolean closed) {
            this.appointment.closed = closed;
            return this;
        }

        public AppointmentResponse build() {
            return appointment;
        }
    }
}
