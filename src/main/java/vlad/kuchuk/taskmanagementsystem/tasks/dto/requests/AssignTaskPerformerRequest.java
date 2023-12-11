package vlad.kuchuk.taskmanagementsystem.tasks.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request to assign task to a specific user")
public class AssignTaskPerformerRequest {
    @Positive
    @NotNull
    private Long assigneeId;
}
