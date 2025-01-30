package org.burgas.employeeservice.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class PositionEmployeePK {

    private Long positionId;
    private Long employeeId;

    public PositionEmployeePK() {
    }

    @SuppressWarnings("unused")
    public Long getPositionId() {
        return positionId;
    }

    @SuppressWarnings("unused")
    public Long getEmployeeId() {
        return employeeId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final PositionEmployeePK positionEmployeePK;

        public Builder() {
            positionEmployeePK = new PositionEmployeePK();
        }

        public Builder positionId(Long positionId) {
            this.positionEmployeePK.positionId = positionId;
            return this;
        }

        public Builder employeeId(Long employeeId) {
            this.positionEmployeePK.employeeId = employeeId;
            return this;
        }

        public PositionEmployeePK build() {
            return positionEmployeePK;
        }
    }
}
