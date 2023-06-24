package megalab.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import megalab.dto.authentication.AuthenticationResponse;
import megalab.dto.authentication.SignInRequest;
import megalab.dto.authentication.SignUpRequest;
import megalab.service.AuthenticationService;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('USER,')")
    public AuthenticationResponse signUp(@RequestBody SignUpRequest signUpRequest){
        return authenticationService.signUp(signUpRequest);
    }
    @PostMapping("/signIn")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','JOURNALIST')")
    public AuthenticationResponse signIn(@RequestBody SignInRequest signInRequest){
        return authenticationService.signIn(signInRequest);
    }
}
