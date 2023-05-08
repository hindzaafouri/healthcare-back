package tn.esprit.healthcare.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.healthcare.Entities.Appointment;
import tn.esprit.healthcare.Entities.User;
import tn.esprit.healthcare.Repositories.AppointmentRepository;
import tn.esprit.healthcare.Repositories.UserRepository;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService{
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment save(Appointment appointment, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        appointment.setUser(user);
        appointmentRepository.save(appointment);
        return appointment;
    }

    @Override
    public Appointment findById(Long id_appointment) {
        if(appointmentRepository.findById(id_appointment).isPresent()) {
            return appointmentRepository.findById(id_appointment).get();
        }
        return null;
    }


    @Override
    public void delete(Long id_appointment) {
        Appointment appointment = findById(id_appointment);
        appointmentRepository.delete(appointment);
    }

    @Override
    public List<Appointment> findAllByDate(Date date) {
        return appointmentRepository.findAppointmentsByDate(date);
    }



    @Override
    public List<Appointment> findAllByDateBefore(Double day) {
        return appointmentRepository.findAppointmentsByDateBefore(day);
    }




    @Override
    public int countAppointment(Date day) {
        return  appointmentRepository.countAppointment( day);
    }

}
