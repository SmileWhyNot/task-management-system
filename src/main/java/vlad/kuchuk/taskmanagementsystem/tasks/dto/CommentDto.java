package vlad.kuchuk.taskmanagementsystem.tasks.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Comment;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * DTO for {@link Comment}
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class CommentDto implements Serializable {
    private Long id;
    @NotNull
    private String text;
    @NotNull
    private ZonedDateTime creationDate;
}