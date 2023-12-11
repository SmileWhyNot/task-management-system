package vlad.kuchuk.taskmanagementsystem.tasks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select distinct t from Task t " +
           "where (:authorId is null or t.author.id = :authorId) " +
           "and (:assigneeId is null or t.assignee.id = :assigneeId)")
    Page<Task> findByAuthorIdOrAssigneeId(
            @Param("authorId") Long authorId,
            @Param("assigneeId") Long assigneeId,
            Pageable pageable
    );
}
