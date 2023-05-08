package tn.esprit.healthcare.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.healthcare.Entities.Answer;
import tn.esprit.healthcare.Entities.Comment;
import tn.esprit.healthcare.Entities.Thread;
import tn.esprit.healthcare.Entities.User;
import tn.esprit.healthcare.Repositories.AnswerRepository;
import tn.esprit.healthcare.Repositories.CommentRepository;
import tn.esprit.healthcare.Repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentService implements ICommentService {

    @Autowired
    AnswerRepository answerRepository ;
    @Autowired
    CommentRepository commentRepository ;

    @Autowired
    UserRepository userRepository ;
    @Override
    public void addCommentAndAssignToAnswer(Comment comment, Long idAnswer, Long userId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(idAnswer) ;
        if (optionalAnswer.isPresent()) {
            Answer answer = optionalAnswer.get();
            User user = userRepository.findById(userId).get() ;
            Set<Comment> comments = answer.getComments();
            if (comments == null) {
                comments = new HashSet<>();
            }
            comments.add(comment);
            answer.setComments(comments);
            comment.setAnswer(answer);
            comment.setUser(user);
            commentRepository.save(comment);
            answerRepository.save(answer);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Answer not found");
        }
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Set<Comment> findCommentsByAnswer(Long idAnswer) {
        Optional<Answer> optionalAnswer = answerRepository.findById(idAnswer) ;
        if (optionalAnswer.isPresent()) {
            Answer answer = optionalAnswer.get();
            Set<Comment> comments = answer.getComments();
            if (comments == null) {
                comments = new HashSet<>();
            }
            return comments ;
         } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Answer not found");
        }
    }

    @Override
    public Comment findCommentById(Long idComment) {
        return commentRepository.findById(idComment).get() ;
    }
}
