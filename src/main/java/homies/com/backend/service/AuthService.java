package homies.com.backend.service;

import homies.com.backend.dto.LoginRequest;
import homies.com.backend.dto.LoginResponse;
import homies.com.backend.dto.RegisterRequest;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    String register(RegisterRequest request);

}