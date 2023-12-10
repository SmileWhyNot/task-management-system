package vlad.kuchuk.taskmanagementsystem.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import vlad.kuchuk.taskmanagementsystem.comments.entity.Comment;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Task;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    @Email(message = "Not correct email provided")
    private String email;

    @NotNull
    @Size(min = 4, max = 100, message = "Password need to be between 4 and 100 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private Set<Comment> comments;

    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private Set<Task> createdTasks;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private Set<Task> assignedTasks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(password,
                that.password) && role == that.role && Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, role, refreshToken);
    }
}