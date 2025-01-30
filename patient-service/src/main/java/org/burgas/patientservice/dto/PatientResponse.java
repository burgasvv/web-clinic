package org.burgas.patientservice.dto;

public class PatientResponse {

    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String gender;
    private String birthdate;
    private Long identityId;

    public PatientResponse() {
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getFirstname() {
        return firstname;
    }

    @SuppressWarnings("unused")
    public String getLastname() {
        return lastname;
    }

    @SuppressWarnings("unused")
    public String getPatronymic() {
        return patronymic;
    }

    @SuppressWarnings("unused")
    public String getGender() {
        return gender;
    }

    @SuppressWarnings("unused")
    public String getBirthdate() {
        return birthdate;
    }

    @SuppressWarnings("unused")
    public Long getIdentityId() {
        return identityId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final PatientResponse patient;

        public Builder() {
            patient = new PatientResponse();
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

        public Builder gender(String gender) {
            this.patient.gender = gender;
            return this;
        }

        public Builder birthdate(String birthdate) {
            this.patient.birthdate = birthdate;
            return this;
        }

        public Builder identityId(Long identityId) {
            this.patient.identityId = identityId;
            return this;
        }

        public PatientResponse build() {
            return patient;
        }
    }
}
