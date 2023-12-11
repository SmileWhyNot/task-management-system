package vlad.kuchuk.taskmanagementsystem.tasks.dto.requests;

import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FilteredPageableTasksRequest {

    @PositiveOrZero
    private Integer page = 0;
    @Positive
    private Integer size = 5;

//    @JsonSetter
//    public void setPage(Integer page) {
//        this.page = (Objects.isNull(page) ? 0 : page);
//    }
//    @JsonSetter
//    public void setSize(Integer size) {
//        this.size = (Objects.isNull(size) ? 5 : size);
//    }
    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
