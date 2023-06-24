package megalab.dto.authentication;

import lombok.*;
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
    private String email;
    private String password;
    private String phoneNumber;

    public SignUpRequest(String firstName, String lastName, LocalDate dateOfBirth, String email, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
