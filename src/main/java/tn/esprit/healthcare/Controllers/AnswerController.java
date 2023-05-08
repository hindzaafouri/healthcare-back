package tn.esprit.healthcare.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.healthcare.Entities.Answer;
import tn.esprit.healthcare.Entities.Thread;
import tn.esprit.healthcare.Services.IAnswerService;
import tn.esprit.healthcare.Services.IThreadService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/answer-op")
@CrossOrigin(origins = "http://localhost:4200")
public class AnswerController {
    @Autowired
    IAnswerService answerService ;

    @Autowired
    IThreadService threadService ;

    @PostMapping("/add-answer/{idThread}")
    public ResponseEntity<Void> addAnswerAndAssignToThread (@RequestBody Answer answer , @PathVariable Long idThread , @RequestParam Long userId) {
           answerService.addAnswerAndAssignToThread(answer,idThread,userId);
           return ResponseEntity.status(HttpStatus.CREATED).build() ;
    }

    @PutMapping(value = "/update-answer/{idAnswer}")
    public ResponseEntity<Void> updateAnswer(@RequestBody Answer updatedAnswer, @PathVariable Long idAnswer) {
        // Set the id of the updated answer to the id in the path variable
        updatedAnswer.setIdAnswer(idAnswer);
        // Call the service method to update the answer
        answerService.updateAnswer(updatedAnswer);
        // Return a response with no content and a status of OK
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/delete-answer/{idAnswer}")
    public void deleteAnswer (@PathVariable Long idAnswer) {
        Answer answer = answerService.findAnswerById(idAnswer) ;
        answerService.deleteAnswer(answer);
    }

   /*@GetMapping("/{idAnswer}/{idThread}")
    public ResponseEntity<?> findAnswerByIdForThread(@PathVariable  Long idAnswer, @PathVariable Long idThread) {
        try {
            Answer result = answerService.findAnswerByIdForThread(idAnswer, idThread);
            return ResponseEntity.status(HttpStatus.FOUND).body(result);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }*/


    @GetMapping("/sortedByVotes/{idThread}")
    public ResponseEntity<?> getAnswersSortedByVotes (@PathVariable Long idThread) {
        try {
            List<Answer> sortedAnswers = answerService.getAnswersSortedByVotes(idThread);
            return ResponseEntity.ok(sortedAnswers);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @PutMapping("/up/{id}")
    public ResponseEntity<Void> upAnswer (@PathVariable long id) {
        answerService.upAnswer(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/down/{id}")
    public ResponseEntity<Void> downAnswer (@PathVariable long id) {
        answerService.downAnswer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/answers-byThread/{idThread}")
    public ResponseEntity<Set<Answer>> findAnswersByThreadOrderByCreatedAt (@PathVariable Long idThread) {
        Thread thread = threadService.findThreadById(idThread) ;
        Set<Answer> result = answerService.findAnswersByThreadOrderByCreatedAt(thread) ;
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
