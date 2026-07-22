package homies.com.backend.controller;

import homies.com.backend.dto.user.ChangePasswordRequest;
import homies.com.backend.dto.user.UpdateProfileRequest;
import homies.com.backend.dto.user.UserProfileResponse;
import homies.com.backend.exception.BadRequestException;
import homies.com.backend.model.User;
import homies.com.backend.repository.UserRepository;
import homies.com.backend.security.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile() {
        return ResponseEntity.ok(toResponse(currentUserUtil.getCurrentUser()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @RequestBody UpdateProfileRequest request) {

        User user = currentUserUtil.getCurrentUser();

        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName().trim());
        }

        if (request.getPhone() != null && !request.getPhone().trim().matches("\\d{10}")) {
            throw new BadRequestException("Please enter a valid 10-digit phone number.");
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone().trim());
        }

        if (request.getAddress() != null) user.setAddress(request.getAddress());
        if (request.getCity() != null) user.setCity(request.getCity());
        if (request.getState() != null) user.setState(request.getState());
        if (request.getPincode() != null) user.setPincode(request.getPincode());

        User saved = userRepository.save(user);

        return ResponseEntity.ok(toResponse(saved));
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request) {

        User user = currentUserUtil.getCurrentUser();

        if (request.getCurrentPassword() == null
                || !passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Current password is incorrect.");
        }

        if (request.getNewPassword() == null || request.getNewPassword().length() < 6) {
            throw new BadRequestException("New password must be at least 6 characters.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Password updated successfully.");
    }

    private UserProfileResponse toResponse(User user) {
        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setAddress(user.getAddress());
        response.setCity(user.getCity());
        response.setState(user.getState());
        response.setPincode(user.getPincode());
        return response;
    }
}
