package megalab.dto.userInfo;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserInfoResponse {
    private Long id;
    private String nickName;
    private String password;
    private String email;
    private String image;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;

    public UserInfoResponse(Long id, String nickName, String password, String email, String image, ZonedDateTime createdAt, ZonedDateTime modifiedAt) {
        this.id = id;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.image = image;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
