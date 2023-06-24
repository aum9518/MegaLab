package megalab.dto.authentication;

import lombok.*;
import megalab.enums.Gender;
import megalab.enums.Role;


import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String image;
    private String nickName;
    private String email;
    private String password;
    private String phoneNumber;

    public SignUpRequest(String firstName, String lastName, LocalDate dateOfBirth, Gender gender, String image, String nickName, String email, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.image = image;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
