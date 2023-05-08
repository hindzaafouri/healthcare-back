package tn.esprit.healthcare.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.healthcare.Entities.Consultation;


@Repository
public interface ConsultationRepository extends CrudRepository<Consultation,Long> {

}