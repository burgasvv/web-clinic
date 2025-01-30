package org.burgas.employeeservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(PositionEmployeePK.class)
public class PositionEmployee {

    @Id
    private Long positionId;

    @Id
    private Long employeeId;

    public PositionEmployee() {
    }

    @SuppressWarnings("unused")
    public Long getPositionId() {
        return positionId;
    }

    @SuppressWarnings("unused")
    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    @SuppressWarnings("unused")
    public Long getEmployeeId() {
        return employeeId;
    }

    @SuppressWarnings("unused")
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final PositionEmployee positionEmployee;

        public Builder() {
            positionEmployee = new PositionEmployee();
        }

        public Builder positionId(Long positionId) {
            this.positionEmployee.positionId = positionId;
            return this;
        }

        public Builder employeeId(Long employeeId) {
            this.positionEmployee.employeeId = employeeId;
            return this;
        }

        public PositionEmployee build() {
            return positionEmployee;
        }
    }
}
