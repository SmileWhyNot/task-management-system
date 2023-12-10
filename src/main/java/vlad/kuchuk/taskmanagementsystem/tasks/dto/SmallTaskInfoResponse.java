package vlad.kuchuk.taskmanagementsystem.tasks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Priority;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Status;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class SmallTaskInfoResponse {
    private Long id;

    @NotBlank
    private String title;

    private String description;

    private Status status;

    private Priority priority;
}
