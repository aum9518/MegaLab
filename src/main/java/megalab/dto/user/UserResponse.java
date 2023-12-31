package megalab.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserResponse {
   private Long id;
   private String firstName;
   private String lastName;
   private String nickName;
   private String image;
   private String email;

    public UserResponse(Long id, String firstName, String lastName, String nickName, String image, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.image = image;
        this.email = email;
    }
}
