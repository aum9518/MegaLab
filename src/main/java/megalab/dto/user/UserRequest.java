package megalab.dto.user;

import lombok.Builder;
import megalab.enums.Gender;

import java.time.LocalDate;

@Builder
public record UserRequest(String firstName,
                          String lastName,
                          String nickName,
                          String image,
                          String password,
                          String email,
                          LocalDate dateOfBirth,
                          String phoneNumber,
                          Gender gender) {
    public UserRequest {
    }
}
