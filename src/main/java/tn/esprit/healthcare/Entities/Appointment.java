package tn.esprit.healthcare.Entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Appointment implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable= false, updatable= false)
    private Long id_appointment;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;

    private String heure ;
    @Enumerated()
    @Column(name = "StateAppointment", nullable = false)
    private StateAppointment stateAppointment;
    private String department;
    private String message;

    private String medecin;

    public Appointment(){}
    public Appointment(Long id_appointment, Date date, String Heure ,StateAppointment stateAppointment){}

    public Long getId_appointment() {
        return id_appointment;
    }

    public void setId_appointment(Long id_appointment) {
        this.id_appointment = id_appointment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure=heure;
    }

    public StateAppointment getStateAppointment() {
        return stateAppointment;
    }

    public void setStateAppointment(StateAppointment stateAppointment) {
        this.stateAppointment = stateAppointment;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMedecin() {
        return medecin;
    }

    public void setMedecin(String medecin) {
        this.medecin = medecin;
    }

    @JsonIgnore
    @ManyToOne
    User user ;
}
