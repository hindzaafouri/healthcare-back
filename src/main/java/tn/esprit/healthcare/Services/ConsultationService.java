package tn.esprit.healthcare.Services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.healthcare.Entities.Consultation;
import tn.esprit.healthcare.Entities.User;
import tn.esprit.healthcare.Repositories.ConsultationRepository;
import tn.esprit.healthcare.Repositories.UserRepository;

import java.util.List;


@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Consultation> getAllConsultations(){
        return (List<Consultation>) consultationRepository.findAll();
    }



    public Consultation AddConsultation(Consultation consultation, Long id){
         User doctor = userRepository.findById(id).get();
         consultation.setDoctor(doctor);
        return this.consultationRepository.save(consultation);
    }
    public Consultation EditConsultation(Consultation consultation,Long id){
        Consultation consultation2 = getConsultationById(id);
        consultation2.setDescription(consultation.getDescription());
        consultation2.setStart_date(consultation.getStart_date());
        consultation2.setEnd_date(consultation.getEnd_date());
        consultation2.setUrl_meeting(consultation.getUrl_meeting());
        consultation2.setPatient(consultation.getPatient());

        return consultationRepository.save(consultation2);
    }
    public Consultation getConsultationById(Long id){
        return  consultationRepository.findById(id).get();
    }

//    public String createMeeting(Date date, String heure, String medecinNom) {
//        try {
//            // Création de l'objet Calendar
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//
//            // Conversion de l'heure en format HH:mm
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//            Date heureDate = sdf.parse(heure);
//            calendar.set(Calendar.HOUR_OF_DAY, heureDate.getHours());
//            calendar.set(Calendar.MINUTE, heureDate.getMinutes());
//
//            // Création de l'objet EventDateTime pour la date et l'heure de début de la réunion
//            EventDateTime startDateTime = new EventDateTime();
//            startDateTime.setDateTime(new DateTime(calendar.getTime()));
//
//            // Ajout d'une durée de 1 heure à la réunion
//            calendar.add(Calendar.HOUR_OF_DAY, 1);
//
//            // Création de l'objet EventDateTime pour la date et l'heure de fin de la réunion
//            EventDateTime endDateTime = new EventDateTime();
//            endDateTime.setDateTime(new DateTime(calendar.getTime()));
//
//            // Création de l'objet Event pour la réunion
//            Event event = new Event();
//            event.setSummary("Réunion avec le Dr. " + medecinNom);
//            event.setLocation("Google Meet");
//            event.setDescription("Réunion avec le Dr. " + medecinNom + " via Google Meet");
//            event.setStart(startDateTime);
//            event.setEnd(endDateTime);
//
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
//





            public void deleteConsultation(Long id){
        consultationRepository.deleteById(id);
    }



}
