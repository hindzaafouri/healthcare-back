package tn.esprit.healthcare.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.healthcare.Entities.Answer;
import tn.esprit.healthcare.Entities.Thread;
import tn.esprit.healthcare.Entities.User;
import tn.esprit.healthcare.Repositories.AnswerRepository;
import tn.esprit.healthcare.Repositories.ThreadRepository;
import tn.esprit.healthcare.Repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AnswerService implements IAnswerService{

    @Autowired
    ThreadRepository threadRepository ;

    @Autowired
    AnswerRepository answerRepository ;

    @Autowired
    UserRepository userRepository ;

    @Override
    public void addAnswerAndAssignToThread(Answer answer, Long idThread , Long userId) {
        Optional<Thread> optionalThread = threadRepository.findById(idThread);
        if (optionalThread.isPresent()) {
            Thread thread = optionalThread.get();
            Set<Answer> answers = thread.getAnswers();
            if (answers == null) {
                answers = new HashSet<>();
            }
            answers.add(answer);
            thread.setAnswers(answers);
            User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            answer.setUser(user);
            answer.setThread(thread);
            answer.setCreatedAt(LocalDateTime.now());
            answerRepository.save(answer);
            threadRepository.save(thread);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Thread not found");
        }
    }

    @Override
    public void updateAnswer(Answer updatedAnswer) {
        // Check if the answer already exists in the database
        Optional<Answer> answerOptional = answerRepository.findById(updatedAnswer.getIdAnswer());
        if (answerOptional.isPresent()) {
            Answer answer = answerOptional.get();
            // Update the answer fields
            answer.setAnswer(updatedAnswer.getAnswer());
            updatedAnswer.setCreatedAt(answer.getCreatedAt());
            updatedAnswer.setUser(answer.getUser());
            updatedAnswer.setThread(answer.getThread());

            // Save the updated answer to the database
            answerRepository.save(answer);
        } else {
            throw new EntityNotFoundException("Answer with ID " + updatedAnswer.getIdAnswer() + " not found");

        }
    }

    @Override
    public void upAnswer(Long id) {
        Answer answer = answerRepository.findById(id).get() ;
        int votes = answer.getVotes() ;
        votes++ ;
        answer.setVotes(votes);
        answerRepository.save(answer) ;
    }

    @Override
    public void downAnswer(Long id) {
        Answer answer = answerRepository.findById(id).get() ;
        int votes = answer.getVotes() ;
        votes-- ;
        answer.setVotes(votes);
        answerRepository.save(answer) ;
    }


    @Override
                public void deleteAnswer(Answer answer) {
                    answerRepository.delete(answer);
                }


    /*@Override
    public Answer findAnswerByIdForThread(Long idAnswer, Long idThread) {
        Optional<Thread> optionalThread = threadRepository.findById(idThread);
        if (optionalThread.isPresent()) {
            Thread thread = optionalThread.get();
            Set<Answer> answers = thread.getAnswers() ;
            for ( Answer answer : answers) {
                if (answer.getIdAnswer() == idAnswer)
                    return answer ;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Thread not found");
    }*/

    @Override
    public Answer findAnswerById(Long idAnswer) {
        return answerRepository.findById(idAnswer).get() ;
    }

    @Override
    public List<Answer> getAnswersSortedByVotes(Long idThread) {
        Optional<Thread> optionalThread = threadRepository.findById(idThread);
        if (optionalThread.isPresent()) {
            Thread thread = optionalThread.get();
            List<Answer> answers = new ArrayList<>(thread.getAnswers());
            answers.sort(Comparator.comparingInt(Answer::getVotes).reversed());
            return answers;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Thread not found");
        }
    }

    @Override
    public Set<Answer> findAnswersByThreadOrderByCreatedAt(Thread thread) {
        return answerRepository.findAnswersByThreadOrderByCreatedAt(thread) ;
    }


}
