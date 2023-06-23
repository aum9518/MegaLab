package megalab.repository;

import megalab.entity.User;
import megalab.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional <User> getUserByEmail(String email);
}