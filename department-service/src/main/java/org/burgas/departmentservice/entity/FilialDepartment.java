package org.burgas.departmentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(FilialDepartmentPK.class)
public class FilialDepartment {

    @Id
    private Long filialId;

    @Id
    private Long departmentId;

    public FilialDepartment() {
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

        private final FilialDepartment filialDepartment;

        public Builder() {
            filialDepartment = new FilialDepartment();
        }

        public Builder filialId(Long filialId) {
            this.filialDepartment.filialId = filialId;
            return this;
        }

        public Builder departmentId(Long departmentId) {
            this.filialDepartment.departmentId = departmentId;
            return this;
        }

        public FilialDepartment build() {
            return filialDepartment;
        }
    }
}
