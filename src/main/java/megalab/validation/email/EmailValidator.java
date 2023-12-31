package megalab.validation.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import megalab.entity.User;
import megalab.entity.UserInfo;
import megalab.repository.UserInfoRepository;
import megalab.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<EmailValid,String> {
    private final UserInfoRepository userInfoRepository;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        List<UserInfo> all1 = userInfoRepository.findAll();
        boolean isFalse = true;
        for (UserInfo u:all1) {
            if (u.getEmail().equals(email)){
                isFalse = false;
            }
        }
        return isFalse;
    }
}
