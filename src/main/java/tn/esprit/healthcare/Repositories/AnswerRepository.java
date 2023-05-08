package tn.esprit.healthcare.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.healthcare.Entities.Answer;
import tn.esprit.healthcare.Entities.Thread;

import java.util.Set;

@Repository
public interface AnswerRepository extends CrudRepository<Answer,Long> {

    Set<Answer> findAnswersByThreadOrderByCreatedAt (Thread thread) ;

}
