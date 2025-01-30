package org.burgas.appointmentservice.repository;

import org.burgas.appointmentservice.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAppointmentsByPatientId(Long patientId);

    List<Appointment> findAppointmentsByEmployeeId(Long employeeId);
}
