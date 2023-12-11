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
@Schema(description = "Response for successful authentication")
public class AuthenticationResponse {
    private String token;
    private String refreshToken;
}
