package megalab.dto.userInfo;

import lombok.Builder;

@Builder
public record UserInfoRequest(String nickName,
                              String password,
                              String email,
                              String image) {
    public UserInfoRequest {
    }
}
