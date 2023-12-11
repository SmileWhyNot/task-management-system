package vlad.kuchuk.taskmanagementsystem.tasks.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "Request for getting tasks by author or assignee with filtering and pagination")
public class FilteredPageableTasksRequest {

    @PositiveOrZero
    private Integer page = 0;
    @Positive
    private Integer size = 5;

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
