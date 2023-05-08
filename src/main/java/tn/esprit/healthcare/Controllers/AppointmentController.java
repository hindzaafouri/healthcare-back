package tn.esprit.healthcare.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.healthcare.Entities.Appointment;
import tn.esprit.healthcare.Repositories.AppointmentRepository;
import tn.esprit.healthcare.Services.AppointmentService;

import java.util.Date;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    private AppointmentRepository appointmentRepository;


    @GetMapping("/liste")
    public ResponseEntity<List<Appointment>> getAppointments(){
        return new ResponseEntity<>(appointmentService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/liste")
    public ResponseEntity<Appointment> saveAppointment(@RequestBody Appointment appointment, @RequestParam Long userId){
        Appointment appointmentO =appointmentService.save(appointment,userId);
        return new ResponseEntity<Appointment>(appointmentO, HttpStatus.OK);
    }

    @GetMapping("/liste/{id_appointment}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable("id_appointment")Long id_appointment){
        Appointment appointment =appointmentService.findById(id_appointment);
        return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
    }

    @DeleteMapping("/liste/{id_appointment}")
    public ResponseEntity<String> deleteAppointment(@PathVariable("id_appointment")Long id_appointment){
        appointmentService.delete(id_appointment);
        return new ResponseEntity<String>("Appointment is deleted successufully!", HttpStatus.OK);
    }
/*
    @PutMapping("/update/{id_appointment}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id_appointment, @RequestBody Appointment appointment){
        Appointment appointmentO = appointmentService.findById(id_appointment);


        if(appointmentO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            appointmentO.setDate(appointment.getDate());
            appointmentO.setHeure(appointment.getHeure());
            appointmentO.setStateAppointment(appointment.getStateAppointment());
            appointmentO.setDepartment(appointment.getDepartment());
            appointmentO.setMessage(appointment.getMessage());
            appointmentO.setMedecin(appointment.getMedecin());

            appointmentO.setUser(appointment.getUser());
            return new ResponseEntity<>(appointmentService.save(appointmentO,userId), HttpStatus.CREATED);
        }catch(DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
    // find by date

    @GetMapping("/getByDate/{date}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){

        return new ResponseEntity<List<Appointment>>(appointmentService.findAllByDate(date), HttpStatus.OK);
    }


// find defore date for sending mail

    @GetMapping("/getByDateBefore")
    public List<Appointment> getAppointmentsBeforeDate(   @RequestParam("day") Double day) {
        return  appointmentService.findAllByDateBefore(day);

    }


    @GetMapping("/countApp")
    public int countAppointment(@RequestParam("date")  @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return appointmentService.countAppointment(date);

    }

}
