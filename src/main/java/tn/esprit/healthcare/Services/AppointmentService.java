package tn.esprit.healthcare.Services;

import tn.esprit.healthcare.Entities.Appointment;

import java.util.Date;
import java.util.List;

public interface AppointmentService {
    public List<Appointment> findAll();

    public Appointment save(Appointment appointment, Long userId);

    public Appointment findById(Long id_appointment);

    public void delete(Long id_appointment);
    public List<Appointment> findAllByDate(Date date);



    public  List<Appointment> findAllByDateBefore(Double day);


    public int countAppointment(Date day);
}
