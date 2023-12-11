package vlad.kuchuk.taskmanagementsystem.tasks.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignTaskPerformerRequest {
    @Positive
    @NotNull
    private Long assigneeId;
}
