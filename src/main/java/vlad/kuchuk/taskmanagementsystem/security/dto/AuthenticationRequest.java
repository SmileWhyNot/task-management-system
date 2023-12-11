package vlad.kuchuk.taskmanagementsystem.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = """
                      Authentication request\s
                      Uses this data to authenticate or register user\s
                      """)
public class AuthenticationRequest {
    private String email;
    private String password;
}
