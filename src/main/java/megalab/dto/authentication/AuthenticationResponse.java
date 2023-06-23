package megalab.dto.authentication;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String token;
    private String email;
    private String role;

    public AuthenticationResponse(String token, String email, String role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }
}
