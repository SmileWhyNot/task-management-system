package vlad.kuchuk.taskmanagementsystem.tasks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Priority;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Status;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link vlad.kuchuk.taskmanagementsystem.tasks.entity.Task}
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class TaskDto implements Serializable {
    private Long id;

    @NotBlank
    private String title;

    private String description;

    private Status status;

    private Priority priority;

    private Set<CommentDto> comments;

    private SmallUserDto author;

    private SmallUserDto assignee;
}