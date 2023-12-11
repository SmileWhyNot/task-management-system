package vlad.kuchuk.taskmanagementsystem.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "User DTO for small response without password, role and tokens")
public class SmallUserDto implements Serializable {
    private Long id;
    @NotNull
    @Email(message = "Not correct email provided")
    private String email;
}
