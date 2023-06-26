package megalab.validation.nickName;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import megalab.entity.UserInfo;
import megalab.repository.UserInfoRepository;

import java.util.List;
@RequiredArgsConstructor
public class NickNameValidator implements ConstraintValidator<NickNameValid,String> {
    private final UserInfoRepository userInfoRepository;
    @Override
    public boolean isValid(String nickName, ConstraintValidatorContext context) {
        List<UserInfo> all1 = userInfoRepository.findAll();
        boolean isFalse = true;
        for (UserInfo u:all1) {
            if (u.getNickName().equals(nickName)){
                isFalse = false;
            }
        }
        return isFalse;
    }
}
