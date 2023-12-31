package megalab.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import megalab.dto.authentication.AuthenticationResponse;
import megalab.dto.authentication.SignInRequest;
import megalab.dto.authentication.SignUpRequest;
import megalab.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationApi {
    private final AuthenticationService authenticationService;
    @PostMapping("/signUp")
    public AuthenticationResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest){
        return authenticationService.signUp(signUpRequest);
    }
    @PostMapping("/signIn")
    public AuthenticationResponse signIn(@RequestBody @Valid SignInRequest signInRequest){
        return authenticationService.signIn(signInRequest);
    }
}
