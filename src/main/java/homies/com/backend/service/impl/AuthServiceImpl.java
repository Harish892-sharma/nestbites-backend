package homies.com.backend.service.impl;

import homies.com.backend.dto.LoginRequest;
import homies.com.backend.dto.LoginResponse;
import homies.com.backend.dto.RegisterRequest;
import homies.com.backend.exception.BadRequestException;
import homies.com.backend.model.User;
import homies.com.backend.repository.UserRepository;
import homies.com.backend.security.JwtUtil;
import homies.com.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    @Override
    public String register(RegisterRequest request) {

        // Server-side validation — the frontend form isn't the only way
        // in, so every rule the UI enforces must be enforced here too.
        if (request.getName() == null || request.getName().trim().length() < 2) {
            throw new BadRequestException("Please enter your full name.");
        }

        if (request.getEmail() == null || !EMAIL_PATTERN.matcher(request.getEmail().trim()).matches()) {
            throw new BadRequestException("Please enter a valid email address.");
        }

        if (request.getPhone() == null || !request.getPhone().trim().matches("\\d{10}")) {
            throw new BadRequestException("Please enter a valid 10-digit phone number.");
        }

        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new BadRequestException("Password must be at least 6 characters.");
        }

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException(
                    "An account with this email already exists — try logging in instead.");
        }

        User user = new User();

        user.setName(request.getName().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPhone(request.getPhone().trim());

        // Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Default Role — public registration can ONLY create CUSTOMER or
        // CHEF accounts. Nobody can hand themselves ADMIN through this
        // endpoint, no matter what they put in the request body.
        String requestedRole = request.getRole() == null
                ? ""
                : request.getRole().trim().toUpperCase();

        if (requestedRole.equals("CHEF")) {
            user.setRole("CHEF");
        } else {
            user.setRole("CUSTOMER");
        }

        userRepository.save(user);

        return "Registration Successful";
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        if (request.getEmail() == null || request.getPassword() == null) {
            throw new BadRequestException("Email and password are required.");
        }

        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new BadRequestException(
                        "No account found with this email."));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new BadRequestException("Incorrect password.");
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