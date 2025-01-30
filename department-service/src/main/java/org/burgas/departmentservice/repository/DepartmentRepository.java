package org.burgas.departmentservice.repository;

import org.burgas.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    select d.* from department d join filial_department fd on d.id = fd.department_id
                                        where fd.filial_id = :filialId
                    """
    )
    List<Department> findDepartmentsByFilialId(Long filialId);
}
