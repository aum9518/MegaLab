package megalab.repository;

import megalab.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> getUserInfoByNickName(String nickName);

    Optional<UserInfo> getUserByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByNickName(String nickName);

}