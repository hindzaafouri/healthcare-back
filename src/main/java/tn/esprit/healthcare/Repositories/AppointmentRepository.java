package tn.esprit.healthcare.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.healthcare.Entities.Appointment;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAppointmentsByDate(Date date);
    // List<Appointment> findAppointmentsByDateAppBefore(Date dateApp);


    @Query("SELECT a FROM Appointment a WHERE a.date BETWEEN CURRENT_DATE  AND CURRENT_DATE + :day ")
    List<Appointment> findAppointmentsByDateBefore( @Param("day") Double day);




    @Query("SELECT count(*) FROM Appointment a WHERE a.date = :date ")
    int countAppointment(@Param("date") Date date);

}
