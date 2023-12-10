package vlad.kuchuk.taskmanagementsystem.tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
