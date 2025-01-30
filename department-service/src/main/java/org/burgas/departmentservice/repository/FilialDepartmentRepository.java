package org.burgas.departmentservice.repository;

import org.burgas.departmentservice.entity.FilialDepartment;
import org.burgas.departmentservice.entity.FilialDepartmentPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilialDepartmentRepository extends JpaRepository<FilialDepartment, FilialDepartmentPK> {

    void deleteFilialDepartmentByFilialIdAndDepartmentId(Long filialId, Long departmentId);
}
