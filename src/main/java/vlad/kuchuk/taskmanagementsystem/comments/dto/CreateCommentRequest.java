package vlad.kuchuk.taskmanagementsystem.comments.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class CreateCommentRequest implements Serializable {
    @NotNull
    private String text;
    @NotNull
    private Long taskId;
    @NotNull
    private Long userId;
}
