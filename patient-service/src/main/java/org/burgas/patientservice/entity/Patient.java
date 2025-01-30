package org.burgas.patientservice.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate birthdate;
    private Long identityId;

    public Patient() {
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
    public LocalDate getBirthdate() {
        return birthdate;
    }

    @SuppressWarnings("unused")
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @SuppressWarnings("unused")
    public Long getIdentityId() {
        return identityId;
    }

    @SuppressWarnings("unused")
    public void setIdentityId(Long authorityId) {
        this.identityId = authorityId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Patient patient;

        public Builder() {
            patient = new Patient();
        }

        public Builder id(Long id) {
            this.patient.id = id;
            return this;
        }

        public Builder firstname(String firstname) {
            this.patient.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.patient.lastname = lastname;
            return this;
        }

        public Builder patronymic(String patronymic) {
            this.patient.patronymic = patronymic;
            return this;
        }

        public Builder gender(Gender gender) {
            this.patient.gender = gender;
            return this;
        }

        public Builder birthdate(LocalDate birthdate) {
            this.patient.birthdate = birthdate;
            return this;
        }

        public Builder identityId(Long identityId) {
            this.patient.identityId = identityId;
            return this;
        }

        public Patient build() {
            return patient;
        }
    }
}
