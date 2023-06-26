package megalab.dto.authentication;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import megalab.validation.notNull.NotNullValid;

@Getter
@Setter
@Builder
public class SignInRequest {
    @NotNullValid
    private String nickname;
    @NotNullValid
    private String password;

    public SignInRequest(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
