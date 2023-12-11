package vlad.kuchuk.taskmanagementsystem.comments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "Create Comment Request")
public class CreateCommentRequest implements Serializable {
    @NotBlank
    private String text;
    @NotNull
    private Long taskId;
    @NotNull
    private Long userId;
}
