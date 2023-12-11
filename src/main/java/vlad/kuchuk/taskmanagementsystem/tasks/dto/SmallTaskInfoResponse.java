package vlad.kuchuk.taskmanagementsystem.tasks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Priority;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Status;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "Small task info response without comments, author and assignee")
public class SmallTaskInfoResponse implements Serializable {
    private Long id;

    @NotBlank
    private String title;

    private String description;

    private Status status;

    private Priority priority;
}
