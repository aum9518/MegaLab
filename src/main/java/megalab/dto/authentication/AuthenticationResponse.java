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

    public AuthenticationResponse(String token, String email) {
        this.token = token;
        this.email = email;
    }
}
