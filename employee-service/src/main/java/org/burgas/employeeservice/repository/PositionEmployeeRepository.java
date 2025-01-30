package org.burgas.employeeservice.repository;

import org.burgas.employeeservice.entity.PositionEmployee;
import org.burgas.employeeservice.entity.PositionEmployeePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionEmployeeRepository extends JpaRepository<PositionEmployee, PositionEmployeePK> {

    void deletePositionEmployeeByEmployeeId(Long employeeId);
}
