package tn.esprit.healthcare.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.healthcare.Entities.Consultation;
import tn.esprit.healthcare.Services.ConsultationService;


@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @GetMapping("/listeConsultations")
    public ResponseEntity<List<Consultation>> getConsultations(){
        return new ResponseEntity<>(consultationService.getAllConsultations(), HttpStatus.OK);

    }

    @PostMapping("/AddConsultation/{id}")
    public ResponseEntity<Consultation> saveConsultation(@RequestBody Consultation consultation, @PathVariable Long id){
        Consultation consultation2 =consultationService.AddConsultation(consultation,id);
        return new ResponseEntity<Consultation>(consultation2, HttpStatus.OK);
    }

    @GetMapping("/Consultation/{id}")
    public ResponseEntity<Consultation> getConsultation(@PathVariable("id")Long id){
        Consultation consultation =consultationService.getConsultationById(id);
        return new ResponseEntity<Consultation>(consultation, HttpStatus.OK);
    }

    @DeleteMapping("/Consultation/{id}")
    public ResponseEntity<String> deleteConsultation(@PathVariable("id")Long id){
        consultationService.deleteConsultation(id);
        return new ResponseEntity<String>("Consultation is deleted successufully!", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Consultation> updateConsultation(@PathVariable Long id, @RequestBody Consultation consultation){

        Consultation consultation2= consultationService.EditConsultation(consultation, id);

        return new ResponseEntity<Consultation>(consultation2,HttpStatus.OK);
    }

}
