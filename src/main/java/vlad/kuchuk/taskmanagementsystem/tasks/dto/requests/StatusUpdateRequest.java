package vlad.kuchuk.taskmanagementsystem.tasks.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for updating task status")
public class StatusUpdateRequest {
    private Status status;
}
