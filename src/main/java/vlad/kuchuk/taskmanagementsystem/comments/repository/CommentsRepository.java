package vlad.kuchuk.taskmanagementsystem.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlad.kuchuk.taskmanagementsystem.comments.entity.Comment;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
    List<Comment> getCommentsByTaskId(Long taskId);
}
