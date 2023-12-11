package vlad.kuchuk.taskmanagementsystem.tasks.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusUpdateRequest {
    private Status status;
}
