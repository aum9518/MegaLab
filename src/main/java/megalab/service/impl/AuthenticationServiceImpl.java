package megalab.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import megalab.config.JwtService;
import megalab.dto.authentication.AuthenticationResponse;
import megalab.dto.authentication.SignInRequest;
import megalab.dto.authentication.SignUpRequest;
import megalab.entity.User;
import megalab.entity.UserInfo;
import megalab.enums.Role;
import megalab.exception.AlreadyExistException;
import megalab.exception.BadCredentialException;
import megalab.repository.UserInfoRepository;
import megalab.repository.UserRepository;
import megalab.service.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (userInfoRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AlreadyExistException("User with email %s already exist".formatted(signUpRequest.getEmail()));
        }
        if (userInfoRepository.existsByNickName(signUpRequest.getNickName())) {
            throw new AlreadyExistException("User with nickName %s already exist".formatted(signUpRequest.getNickName()));
        }
        User user = User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .dateOfBirth(signUpRequest.getDateOfBirth())
                .gender(signUpRequest.getGender())
                .image(signUpRequest.getImage())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .isBlock(false).build();
        UserInfo userInfo = UserInfo.builder()
                .nickName(signUpRequest.getNickName())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .createdAt(ZonedDateTime.now())
                .modifiedAt(ZonedDateTime.now())
                .role(Role.READER)
                .build();
        userRepository.save(user);
        userInfoRepository.save(userInfo);
        user.setUserInfo(userInfo);
        userInfo.setUser(user);
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(userInfo))
                .email(userInfo.getEmail()).build();
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        UserInfo userInfo = userInfoRepository.getUserByEmail(signInRequest.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("User with email: " + signInRequest.getEmail() + " not found!")
        );
        if (signInRequest.getPassword().isBlank()) {
            throw new BadCredentialException("Password is blank");
        }
        if (!passwordEncoder.matches(signInRequest.getPassword(), userInfo.getPassword())) {
            throw new BadCredentialException("Wrong password!");
        }

        String token = jwtService.generateToken(userInfo);
        return AuthenticationResponse
                .builder()
                .token(token)
                .email(userInfo.getEmail())
                .build();
    }
}
