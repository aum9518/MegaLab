package megalab.service;

import megalab.dto.authentication.AuthenticationResponse;
import megalab.dto.authentication.SignInRequest;
import megalab.dto.authentication.SignUpRequest;

public interface AuthenticationService {
    AuthenticationResponse signUp(SignUpRequest signUpRequest);
    AuthenticationResponse signIn(SignInRequest signInRequest);
}
