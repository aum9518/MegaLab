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
import megalab.exception.NotFoundException;
import megalab.repository.UserInfoRepository;
import megalab.repository.UserRepository;
import megalab.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;


    @Override
    public UserPagination getAllUsers(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<UserResponse> allUsers = userRepository.getAllUsers(pageable);
        return UserPagination.builder()
                .userResponses(allUsers.getContent())
                .currentPage(allUsers.getNumber()+1)
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
        user.setBlock(false);
        userRepository.save(user);

        userInfo.setNickName(userRequest.nickName());
        userInfo.setPassword(userRequest.password());
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

        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setImage(userRequest.image());
        userRepository.save(user);

        userInfo.setNickName(userRequest.nickName());
        userInfo.setPassword(userRequest.password());
        userInfo.setEmail(userRequest.email());
        userInfo.setModifiedAt(ZonedDateTime.now());
        userInfoRepository.save(userInfo);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("User with id:%s successfully saved", userId))
                .build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id:%s is not present", id)));
        UserInfo userInfo = userInfoRepository.findById(user.getUserInfo().getId()).orElseThrow(() -> new NotFoundException(String.format("UserInfo with id:%s is not present", user.getUserInfo().getUser())));
        return null;
    }

    @Override
    public SimpleResponse deleteUser(Long id) {
        return null;
    }
}
