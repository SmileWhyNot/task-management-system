package vlad.kuchuk.taskmanagementsystem.tasks.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class SmallUserDto implements Serializable {
    private Long id;
    @NotNull
    @Email(message = "Not correct email provided")
    private String email;
}
