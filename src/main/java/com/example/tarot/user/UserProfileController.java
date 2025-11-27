package com.example.tarot.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me")
public class UserProfileController {

    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;

    public UserProfileController(CurrentUserService currentUserService, UserRepository userRepository) {
        this.currentUserService = currentUserService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public UserProfileDto getProfile() {
        User u = currentUserService.getCurrentUserOrThrow();
        return new UserProfileDto(
                u.getId(),
                u.getEmail(),
                u.getDisplayName(),
                u.getSubscriptionType(),
                u.getBonusAiReadings(),
                u.hasActiveProSubscription()
        );
    }

    @PatchMapping
    public ResponseEntity<UserProfileDto> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        User u = currentUserService.getCurrentUserOrThrow();
        if (request.getDisplayName() != null) {
            u.setDisplayName(request.getDisplayName());
        }
        userRepository.save(u);
        UserProfileDto dto = new UserProfileDto(
                u.getId(),
                u.getEmail(),
                u.getDisplayName(),
                u.getSubscriptionType(),
                u.getBonusAiReadings(),
                u.hasActiveProSubscription()
        );
        return ResponseEntity.ok(dto);
    }
}
