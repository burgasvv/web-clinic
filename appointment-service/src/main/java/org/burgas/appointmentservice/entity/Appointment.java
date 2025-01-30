package org.burgas.appointmentservice.entity;

import jakarta.persistence.*;
import org.burgas.patientservice.entity.Gender;

import java.time.LocalDate;
import java.time.LocalTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private String description;
    private LocalDate date;
    private LocalTime hours;
    private Long employeeId;
    private Long patientId;
    private Boolean closed;

    public Appointment() {
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
    public Gender getGender() {
        return gender;
    }

    @SuppressWarnings("unused")
    public void setGender(Gender gender) {
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
    public LocalDate getDate() {
        return date;
    }

    @SuppressWarnings("unused")
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @SuppressWarnings("unused")
    public LocalTime getHours() {
        return hours;
    }

    @SuppressWarnings("unused")
    public void setHours(LocalTime hours) {
        this.hours = hours;
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
    public Long getPatientId() {
        return patientId;
    }

    @SuppressWarnings("unused")
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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

        private final Appointment appointment;

        public Builder() {
            appointment = new Appointment();
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

        public Builder gender(Gender gender) {
            this.appointment.gender = gender;
            return this;
        }

        public Builder description(String description) {
            this.appointment.description = description;
            return this;
        }

        public Builder date(LocalDate date) {
            this.appointment.date = date;
            return this;
        }

        public Builder hours(LocalTime hours) {
            this.appointment.hours = hours;
            return this;
        }

        public Builder employeeId(Long employeeId) {
            this.appointment.employeeId = employeeId;
            return this;
        }

        public Builder patientId(Long patientId) {
            this.appointment.patientId = patientId;
            return this;
        }

        public Builder closed(Boolean closed) {
            this.appointment.closed = closed;
            return this;
        }

        public Appointment build() {
            return appointment;
        }
    }
}
