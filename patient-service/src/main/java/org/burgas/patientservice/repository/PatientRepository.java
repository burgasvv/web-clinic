package org.burgas.patientservice.repository;

import org.burgas.patientservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    select p.* from patient p where concat(
                        p.firstname,' ',p.lastname,' ',p.patronymic,' ',
                                            p.lastname,' ',p.firstname,' ',p.patronymic,' ',p.firstname,' '
                    ) ilike concat('%',:flp,'%')
                    """
    )
    List<Patient> findPatientsByFlp(String flp);

    Optional<Patient> findPatientByIdentityId(Long identityId);
}
