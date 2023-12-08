package vlad.kuchuk.taskmanagementsystem.tasks.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import vlad.kuchuk.taskmanagementsystem.security.entity.UserEntity;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Priority priority;

    @OneToMany(mappedBy = "task", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private Set<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "assignee_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ToString.Exclude
    private UserEntity assignee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && status == task.status && priority == task.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, priority);
    }
}
