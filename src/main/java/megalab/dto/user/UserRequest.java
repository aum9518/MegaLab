package megalab.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NonNull;
import megalab.enums.Gender;
import megalab.validation.dateOfBirth.DateOfBirthValid;
import megalab.validation.email.EmailValid;
import megalab.validation.nickName.NickNameValid;
import megalab.validation.notNull.NotNullValid;
import megalab.validation.password.PasswordValid;
import megalab.validation.phoneNuber.PhoneNumberValidation;

import java.time.LocalDate;

@Builder
public record UserRequest(@NotNullValid
                          String firstName,
                          @NotNullValid
                          String lastName,
                          @NotNullValid
                          @NickNameValid
                          String nickName,
                          @NotNullValid
                          String image,
                          @NotNullValid
                          @PasswordValid
                          String password,
                          @NotNullValid
                          @Email
                          @EmailValid
                          String email,
                          @DateOfBirthValid
                          LocalDate dateOfBirth,
                          @NotNullValid
                          @PhoneNumberValidation
                          String phoneNumber,
                          Gender gender) {
    public UserRequest {
    }
}
