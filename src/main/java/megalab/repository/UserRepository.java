package megalab.repository;

import megalab.entity.User;
import megalab.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN u.userInfo uf WHERE uf.nickName = ?1")
    Optional<User> getUserByUserInfoNickName(String nickName);

}