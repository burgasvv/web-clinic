package org.burgas.employeeservice.dto;

import org.burgas.departmentservice.dto.DepartmentResponse;
import org.burgas.departmentservice.dto.FilialResponse;

import java.util.List;

public class EmployeeResponse {

    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String gender;
    private String birthdate;
    private String about;
    private Long identityId;
    private FilialResponse filial;
    private DepartmentResponse department;
    private List<PositionResponse> positions;

    public EmployeeResponse() {
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
    public String getBirthdate() {
        return birthdate;
    }

    @SuppressWarnings("unused")
    public void setBirthdate(String birthdate) {
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
    public FilialResponse getFilial() {
        return filial;
    }

    @SuppressWarnings("unused")
    public void setFilial(FilialResponse filial) {
        this.filial = filial;
    }

    @SuppressWarnings("unused")
    public DepartmentResponse getDepartment() {
        return department;
    }

    @SuppressWarnings("unused")
    public void setDepartment(DepartmentResponse department) {
        this.department = department;
    }

    @SuppressWarnings("unused")
    public List<PositionResponse> getPositions() {
        return positions;
    }

    @SuppressWarnings("unused")
    public void setPositions(List<PositionResponse> positions) {
        this.positions = positions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final EmployeeResponse employee;

        public Builder() {
            employee = new EmployeeResponse();
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

        public Builder gender(String gender) {
            this.employee.gender = gender;
            return this;
        }

        public Builder birthdate(String birthdate) {
            this.employee.birthdate = birthdate;
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

        public Builder filial(FilialResponse filial) {
            this.employee.filial = filial;
            return this;
        }

        public Builder department(DepartmentResponse department) {
            this.employee.department = department;
            return this;
        }

        public Builder positions(List<PositionResponse> positions) {
            this.employee.positions = positions;
            return this;
        }

        public EmployeeResponse build() {
            return employee;
        }
    }
}
