package org.burgas.employeeservice.repository;

import org.burgas.employeeservice.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    select p.* from position p join position_employee pe on p.id = pe.position_id
                                        where pe.employee_id = :employeeId
                    """
    )
    List<Position> findPositionsByEmployeeId(Long employeeId);
}
