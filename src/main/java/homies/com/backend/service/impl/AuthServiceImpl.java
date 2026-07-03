package homies.com.backend.service.impl;

import homies.com.backend.dto.LoginRequest;
import homies.com.backend.dto.LoginResponse;
import homies.com.backend.dto.RegisterRequest;
import homies.com.backend.model.User;
import homies.com.backend.repository.UserRepository;
import homies.com.backend.security.JwtUtil;
import homies.com.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    @Override
    public String register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        // Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Default Role
        if (request.getRole() == null || request.getRole().isBlank()) {
            user.setRole("CUSTOMER");
        } else {
            user.setRole(request.getRole().toUpperCase());
        }

        userRepository.save(user);

        return "Registration Successful";
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Email"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid Password");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );

        return new LoginResponse(
                token,
                user.getRole(),
                user.getId(),
                user.getName()
        );
    }
}