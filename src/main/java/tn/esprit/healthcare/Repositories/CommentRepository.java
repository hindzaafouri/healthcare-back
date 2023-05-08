package tn.esprit.healthcare.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.healthcare.Entities.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment,Long> {
}
