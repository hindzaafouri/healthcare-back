package tn.esprit.healthcare.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.healthcare.Entities.Thread;
import tn.esprit.healthcare.Entities.Topic;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface ThreadRepository extends CrudRepository<Thread,Long> {

    Set<Thread> findByTopicThread (Topic topic) ;
    List<Thread> findThreadByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) ;

    List<Thread> findThreadByStatus (boolean status) ;
}
