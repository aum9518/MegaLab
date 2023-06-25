package megalab.dto.authentication;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignInRequest {
    private String nickname;
    private String password;

    public SignInRequest(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
