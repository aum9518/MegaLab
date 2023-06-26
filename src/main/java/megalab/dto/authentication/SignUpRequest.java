package megalab.dto.authentication;

import jakarta.validation.constraints.Email;
import lombok.*;
import megalab.enums.Gender;
import megalab.validation.dateOfBirth.DateOfBirthValid;
import megalab.validation.email.EmailValid;
import megalab.validation.nickName.NickNameValid;
import megalab.validation.notNull.NotNullValid;
import megalab.validation.password.PasswordValid;
import megalab.validation.phoneNuber.PhoneNumberValidation;


import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class SignUpRequest {
    @NotNullValid
    private String firstName;
    @NotNullValid
    private String lastName;
    @DateOfBirthValid
    private LocalDate dateOfBirth;
    private Gender gender;
    @NotNullValid
    private String image;
    @NotNullValid
    @NickNameValid
    private String nickName;
    @NotNullValid
    @EmailValid
    @Email
    private String email;
    @NotNullValid
    @PasswordValid
    private String password;
    @NotNullValid
    @PhoneNumberValidation
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
