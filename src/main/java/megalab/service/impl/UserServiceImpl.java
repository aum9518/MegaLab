package megalab.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import megalab.dto.SimpleResponse;
import megalab.dto.user.UserPagination;
import megalab.dto.user.UserRequest;
import megalab.dto.user.UserResponse;
import megalab.entity.User;
import megalab.entity.UserInfo;
import megalab.enums.Role;
import megalab.exception.BadRequestException;
import megalab.exception.NotFoundException;
import megalab.repository.UserInfoRepository;
import megalab.repository.UserRepository;
import megalab.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    private User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return userRepository.getUserByUserInfoNickName(name).orElseThrow(() ->{log.error("User with nickName: " + name + " us bit found!");
            return new NotFoundException("User with nickName: " + name + " us bit found!");
        });
    }

    @Override
    public UserPagination getAllUsers(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<UserResponse> allUsers = userRepository.getAllUsers(pageable);
        log.info("Get all users");
        return UserPagination.builder()
                .userResponses(allUsers.getContent())
                .currentPage(allUsers.getNumber() + 1)
                .pageSize(allUsers.getSize())
                .build();
    }

    @Override
    public SimpleResponse saveUser(UserRequest userRequest) {
        User user = new User();
        UserInfo userInfo = new UserInfo();

        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setImage(userRequest.image());
        user.setUserInfo(userInfo);
        user.setDateOfBirth(userRequest.dateOfBirth());
        user.setPhoneNumber(userRequest.phoneNumber());
        user.setGender(userRequest.gender());
        user.setBlock(false);
        log.info("User successfully saved");
        userRepository.save(user);

        userInfo.setNickName(userRequest.nickName());
        userInfo.setPassword(passwordEncoder.encode(userRequest.password()));
        userInfo.setRole(Role.JOURNALIST);
        userInfo.setEmail(userRequest.email());
        userInfo.setModifiedAt(ZonedDateTime.now());
        userInfo.setCreatedAt(ZonedDateTime.now());
        userInfo.setUser(user);
        log.info("UserInfo successfully saved");
        userInfoRepository.save(userInfo);


        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully saved...")
                .build();
    }

    @Override
    public SimpleResponse update(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElseThrow(() ->{log.error(String.format("User with id:%s is not present", userId));
            return new NotFoundException(String.format("User with id:%s is not present", userId));
        });
        UserInfo userInfo = userInfoRepository.findById(user.getUserInfo().getId()).orElseThrow(() -> new NotFoundException(String.format("UserInfo with id:%s is not present", user.getUserInfo().getUser())));
        User authentication = getAuthentication();

        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setImage(userRequest.image());


        userInfo.setNickName(userRequest.nickName());
        userInfo.setPassword(passwordEncoder.encode(userRequest.password()));
        userInfo.setEmail(userRequest.email());
        userInfo.setModifiedAt(ZonedDateTime.now());


        if (authentication.getUserInfo().getRole().equals(Role.ADMIN)) {
            userRepository.save(user);
            userInfoRepository.save(userInfo);
            log.info(String.format("User with id:%s successfully updated", userId));
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message(String.format("User with id:%s successfully updated", userId))
                    .build();
        }
        if (user.equals(authentication)) {
            userRepository.save(user);
            userInfoRepository.save(userInfo);
            log.info(String.format("User with id:%s successfully updated", userId));
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message(String.format("User with id:%s successfully updated", userId))
                    .build();
        } else{log.error("You can not update this user!"); throw new BadRequestException("You can not update this user!");}

    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error(String.format("User with id:%s is not present", id));
            throw new NotFoundException(String.format("User with id:%s is not present", id));});
        UserInfo userInfo = userInfoRepository.findById(user.getUserInfo().getId()).orElseThrow(() -> new NotFoundException(String.format("UserInfo with id:%s is not present", user.getUserInfo().getUser())));
        User user1 = getAuthentication();
        if (user1.getUserInfo().getRole().equals(Role.ADMIN)) {
                log.info("Get user by admin");
            return UserResponse.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(userInfo.getEmail())
                    .image(user.getImage())
                    .nickName(userInfo.getNickName())
                    .build();
        }
        if (user.equals(user1)) {
            log.info(String.format("Get user by %s",user.getFirstName()));
            return UserResponse.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(userInfo.getEmail())
                    .image(user.getImage())
                    .nickName(userInfo.getNickName())
                    .build();
        } else{log.error("You can not get this user!"); throw new BadRequestException("You can not get this user!");}

    }

    @Override
    public SimpleResponse deleteUser(Long id) {
        User authentication = getAuthentication();
        User user = userRepository.findById(id).orElseThrow(() ->{log.error(String.format("User with id:%s is not present", id));
            return new NotFoundException(String.format("User with id:%s is not present", id));
        });

        UserInfo userInfo = userInfoRepository.findById(user.getUserInfo().getId()).orElseThrow(() -> new NotFoundException(String.format("UserInfo with id:%s is not present", user.getUserInfo().getUser())));
        if (authentication.getUserInfo().getRole().equals(Role.ADMIN)) {
                userRepository.deleteById(id);
                log.info("Successfully deleted by Admin...");
                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Successfully deleted...")
                        .build();
            }
            if (user.equals(authentication)) {
                userRepository.deleteById(id);
                log.info("Successfully deleted by user...");
                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Successfully deleted...")
                        .build();
            } else throw new BadRequestException("You can not delete this account");


        }


    @Override
    public UserPagination searchUser(String word, int currentPage, int pageSize) {
        int a = (currentPage-1)*pageSize;
        String query = "select u.id,u.first_name,u.last_name,ui.nick_name,u.image,ui.email from users u join user_info AS ui ON u.id = ui.user_id  WHERE u.first_name ILIKE CONCAT ('%', ? , '%') OR u.first_name ILIKE CONCAT ('%', ? , '%') OR ui.nick_name ILIKE CONCAT ('%',?,'%') LIMIT ? OFFSET ?";
        List<UserResponse> users = jdbcTemplate.query(query,new Object[]{word,word,word,pageSize,a},(rs, row) -> new UserResponse(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("nick_name"),
                rs.getString("image"),
                rs.getString("email")
        ));
        log.info("Searching users");
        return UserPagination.builder()
                .userResponses(users)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();
    }



    @Override
    public SimpleResponse blockOrUnblock(String block, Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->{log.error(String.format("User with id:%s is not present", id));return new NotFoundException(String.format("User with id:%s is not present", id));});
        if (block.equalsIgnoreCase("block")){
            if (!user.isBlock()){
                String query = "update users u set is_block = true where u.id = ?";
                jdbcTemplate.update(query,id);
                log.info(String.format("User with id:%s is blocked",id));
                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Blocked")
                        .build();
            }else throw new BadRequestException(String.format("User with id:%s is already blocked",id));
        } else if (block.equalsIgnoreCase("unblock")) {
            if (user.isBlock()){
                String query = "update users u set is_block = false where u.id = ?";
                jdbcTemplate.update(query,id);
                log.info(String.format("User with id:%s is unblocked",id));
                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Unblocked")
                        .build();
            }else{log.error(String.format("User with id:%s was not blocked",id)); throw new BadRequestException(String.format("User with id:%s was not blocked",id));}
            }
        return null;

    }

    @Override
    public UserPagination filterByBlock(boolean isBlock, int currentPage, int pageSize) {
        int a = (currentPage-1)*pageSize;
        String query = "select u.id,u.first_name,u.last_name,ui.nick_name,u.image,ui.email from users u join user_info as ui on u.id = ui.user_id where u.is_block = ? LIMIT ? OFFSET ?";
        List<UserResponse> list = jdbcTemplate.query(query, new Object[]{isBlock, pageSize, a}, (u, rowNum) -> new UserResponse(
                u.getLong("id"),
                u.getString("first_name"),
                u.getString("last_name"),
                u.getString("nick_name"),
                u.getString("image"),
                u.getString("email")
        ));
            log.info("Filter users by block or unblock");
        return UserPagination.builder()
                .userResponses(list)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();
    }
}



