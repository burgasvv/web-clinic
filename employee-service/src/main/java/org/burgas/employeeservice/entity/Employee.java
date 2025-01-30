package org.burgas.employeeservice.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate birthdate;
    private String about;
    private Long identityId;
    private Long filialId;
    private Long departmentId;

    public Employee() {
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
    public String getAbout() {
        return about;
    }

    @SuppressWarnings("unused")
    public void setAbout(String about) {
        this.about = about;
    }

    @SuppressWarnings("unused")
    public Long getIdentityId() {
        return identityId;
    }

    @SuppressWarnings("unused")
    public void setIdentityId(Long identityId) {
        this.identityId = identityId;
    }

    @SuppressWarnings("unused")
    public Long getFilialId() {
        return filialId;
    }

    @SuppressWarnings("unused")
    public void setFilialId(Long filialId) {
        this.filialId = filialId;
    }

    @SuppressWarnings("unused")
    public Long getDepartmentId() {
        return departmentId;
    }

    @SuppressWarnings("unused")
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Employee employee;

        public Builder() {
            employee = new Employee();
        }

        public Builder id(Long id) {
            this.employee.id = id;
            return this;
        }

        public Builder firstname(String firstname) {
            this.employee.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.employee.lastname = lastname;
            return this;
        }

        public Builder patronymic(String patronymic) {
            this.employee.patronymic = patronymic;
            return this;
        }

        public Builder gender(Gender gender) {
            this.employee.gender = gender;
            return this;
        }

        public Builder birthday(LocalDate birthday) {
            this.employee.birthdate = birthday;
            return this;
        }

        public Builder about(String about) {
            this.employee.about = about;
            return this;
        }

        public Builder identityId(Long identityId) {
            this.employee.identityId = identityId;
            return this;
        }

        public Builder filialId(Long filialId) {
            this.employee.filialId = filialId;
            return this;
        }

        public Builder departmentId(Long departmentId) {
            this.employee.departmentId = departmentId;
            return this;
        }

        public Employee build() {
            return employee;
        }
    }
}
