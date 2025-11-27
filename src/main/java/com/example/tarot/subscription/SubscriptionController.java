package com.example.tarot.subscription;

import com.example.tarot.user.SubscriptionType;
import com.example.tarot.user.User;
import com.example.tarot.user.UserRepository;
import com.example.tarot.user.CurrentUserService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;

    @Value("${app.subscription.activation-secret:change-me}")
    private String activationSecret;

    public SubscriptionController(CurrentUserService currentUserService,
                                  UserRepository userRepository) {
        this.currentUserService = currentUserService;
        this.userRepository = userRepository;
    }

    @PostMapping("/activate")
    public ResponseEntity<Void> activatePro(
            @RequestParam("secret") String secret,
            @RequestParam("months") @Positive int months
    ) {
        if (!activationSecret.equals(secret)) {
            return ResponseEntity.status(403).build();
        }

        User user = currentUserService.getCurrentUserOrThrow();
        user.setSubscriptionType(SubscriptionType.PRO);

        Instant now = Instant.now();
        if (user.getSubscriptionExpiresAt() != null && user.getSubscriptionExpiresAt().isAfter(now)) {
            user.setSubscriptionExpiresAt(user.getSubscriptionExpiresAt().plus(months, ChronoUnit.MONTHS));
        } else {
            user.setSubscriptionExpiresAt(now.plus(months, ChronoUnit.MONTHS));
        }

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
