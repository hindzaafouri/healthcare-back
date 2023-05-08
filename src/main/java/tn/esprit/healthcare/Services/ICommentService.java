package tn.esprit.healthcare.Services;

import tn.esprit.healthcare.Entities.Comment;

import java.util.List;
import java.util.Set;

public interface ICommentService {
    public void addCommentAndAssignToAnswer (Comment comment , Long idAnswer, Long userId) ;

    public void deleteComment (Comment comment) ;

    public Set<Comment> findCommentsByAnswer (Long idAnswer) ;

    public Comment findCommentById (Long idComment) ;


}
