package megalab.dto.user;

import lombok.Builder;

@Builder
public record UserRequest(String firstName,
                          String lastName,
                          String nickName,
                          String image,
                          boolean isBlock,
                          String password,
                          String email) {
    public UserRequest {
    }
}
