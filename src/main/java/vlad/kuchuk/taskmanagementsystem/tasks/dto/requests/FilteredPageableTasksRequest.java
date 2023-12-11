package vlad.kuchuk.taskmanagementsystem.tasks.dto.requests;

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
public class FilteredPageableTasksRequest {

    @PositiveOrZero
    private Integer page = 0;
    @Positive
    private Integer size = 5;

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
