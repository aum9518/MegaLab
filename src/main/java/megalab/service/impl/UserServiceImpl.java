package megalab.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    private User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return userRepository.getUserByUserInfoNickName(name).orElseThrow(() -> new NotFoundException("User with nickName: " + name + " us bit found!"));
    }

    @Override
    public UserPagination getAllUsers(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<UserResponse> allUsers = userRepository.getAllUsers(pageable);
        return UserPagination.builder()
                .userResponses(allUsers.getContent())
                .currentPage(allUsers.getNumber() + 1)
                .pageSize(allUsers.getTotalPages())
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

        userRepository.save(user);

        userInfo.setNickName(userRequest.nickName());
        userInfo.setPassword(passwordEncoder.encode(userRequest.password()));
        userInfo.setRole(Role.JOURNALIST);
        userInfo.setEmail(userRequest.email());
        userInfo.setModifiedAt(ZonedDateTime.now());
        userInfo.setCreatedAt(ZonedDateTime.now());
        userInfo.setUser(user);
        userInfoRepository.save(userInfo);


        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully saved...")
                .build();
    }

    @Override
    public SimpleResponse update(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User with id:%s is not present", userId)));
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
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message(String.format("User with id:%s successfully updated", userId))
                    .build();
        }
        if (user.equals(authentication)) {
            userRepository.save(user);
            userInfoRepository.save(userInfo);
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message(String.format("User with id:%s successfully updated", userId))
                    .build();
        } else throw new BadRequestException("You can not update this user!");

    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id:%s is not present", id)));
        UserInfo userInfo = userInfoRepository.findById(user.getUserInfo().getId()).orElseThrow(() -> new NotFoundException(String.format("UserInfo with id:%s is not present", user.getUserInfo().getUser())));
        User user1 = getAuthentication();
        if (user1.getUserInfo().getRole().equals(Role.ADMIN)) {
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
            return UserResponse.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(userInfo.getEmail())
                    .image(user.getImage())
                    .nickName(userInfo.getNickName())
                    .build();
        } else throw new BadRequestException("You can not get this user!");

    }

    @Override
    public SimpleResponse deleteUser(Long id) {
        User authentication = getAuthentication();
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id:%s is not present", id)));

        UserInfo userInfo = userInfoRepository.findById(user.getUserInfo().getId()).orElseThrow(() -> new NotFoundException(String.format("UserInfo with id:%s is not present", user.getUserInfo().getUser())));
        if (authentication.getUserInfo().getRole().equals(Role.ADMIN)) {

            if (user.getUserInfo().getRole().equals(Role.ADMIN)) {

                userRepository.deleteById(id);
                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Successfully deleted...")
                        .build();
            }
            if (user.equals(authentication)) {
                userRepository.deleteById(id);
                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Successfully deleted...")
                        .build();
            } else throw new BadRequestException("You can not delete this account");


        }
        return null;
    }
}

//    @Override
//    public UserPagination searchUser(String word, int currentPage, int pageSize) {
//        return null;
//    }


