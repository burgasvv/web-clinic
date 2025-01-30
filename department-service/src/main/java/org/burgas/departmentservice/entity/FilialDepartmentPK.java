package org.burgas.departmentservice.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class FilialDepartmentPK {

    private Long filialId;
    private Long departmentId;

    public FilialDepartmentPK() {
    }

    @SuppressWarnings("unused")
    public Long getFilialId() {
        return filialId;
    }

    @SuppressWarnings("unused")
    public Long getDepartmentId() {
        return departmentId;
    }
}
