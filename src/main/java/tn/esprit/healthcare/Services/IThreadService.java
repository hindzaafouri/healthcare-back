package tn.esprit.healthcare.Services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.healthcare.Entities.Answer;
import tn.esprit.healthcare.Entities.Thread;
import tn.esprit.healthcare.Entities.Topic;
import tn.esprit.healthcare.Repositories.ThreadRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IThreadService {

    public Thread addThread (Thread thread);

    public void updateThread (Thread thread) ;

    public void deleteThread (Thread thread) ;

    public Thread findThreadById(Long id) ;

    public Set<Thread> findThreadByKeyWord (String keyword) ;

    //public List<Thread> getThreadsByTimeFrame (String timeFrame) ;

    public List<Thread> getActiveStatus () ;

    public Map<String, Integer>  getThreadCountsByMonthInYear (int year) ;

    public Iterable<Thread> findAll () ;

    public List<String> getTopics () ;

    public void upThread (Long id) ;
    public void downThread (Long id) ;

    public List<Thread> getThreadsSortedByVotes() ;

    public Set<Thread> findThreadByTopic (Topic topic) ;


}
