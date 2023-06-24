package megalab.repository;

import megalab.dto.user.UserResponse;
import megalab.entity.User;
import megalab.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select new megalab.dto.user.UserResponse(u.id,u.firstName,u.lastName,us.nickName,u.image,us.email) from User u join u.userInfo us")
    Page<UserResponse> getAllUsers(Pageable pageable);

}