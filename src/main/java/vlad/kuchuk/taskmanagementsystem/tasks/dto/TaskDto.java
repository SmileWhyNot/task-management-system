package vlad.kuchuk.taskmanagementsystem.tasks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import vlad.kuchuk.taskmanagementsystem.comments.dto.CommentDto;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Priority;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Status;
import vlad.kuchuk.taskmanagementsystem.user.dto.SmallUserDto;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "TaskDto",
        description = "Full DTO for Task entity")
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