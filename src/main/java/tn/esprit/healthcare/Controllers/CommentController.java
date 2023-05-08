package tn.esprit.healthcare.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.healthcare.Entities.Answer;
import tn.esprit.healthcare.Entities.Comment;
import tn.esprit.healthcare.Services.ICommentService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/comment-op")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    @Autowired
    ICommentService commentService ;
    @PostMapping("/add-comment/{idAnswer}")
    public ResponseEntity<Void> addCommentAndAssignToAnswer (@RequestBody Comment comment , @PathVariable Long idAnswer,@RequestParam Long userId) {
        commentService.addCommentAndAssignToAnswer(comment,idAnswer,userId);
        return ResponseEntity.status(HttpStatus.CREATED).build() ;
    }

    @DeleteMapping("/{idComment}")
    public void deleteComment (@PathVariable Long idComment) {
        Comment comment = commentService.findCommentById(idComment) ;
        commentService.deleteComment(comment);
    }

    @GetMapping("/comments-ByAnswer/{idAnswer}")
    public ResponseEntity<Set<Comment>> findCommentsByAnswer (@PathVariable Long idAnswer) {
        Set<Comment> comments  = commentService.findCommentsByAnswer(idAnswer) ;
        return ResponseEntity.ok(comments) ;
    }
}
