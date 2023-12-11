package vlad.kuchuk.taskmanagementsystem.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import vlad.kuchuk.taskmanagementsystem.security.entity.Role;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "Complete User DTO with all necessary fields")
public class UserDto implements Serializable {
    private Long id;

    @NotNull
    @Email(message = "Not correct email provided")
    private String email;

    @NotNull
    @Size(message = "Password need to be between 4 and 100 characters", min = 4, max = 100)
    private String password;

    @NotNull
    private Role role;

    private String refreshToken;
}