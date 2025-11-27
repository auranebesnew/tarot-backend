package com.example.tarot.user;

public class UserProfileDto {

    private Long id;
    private String email;
    private String displayName;
    private SubscriptionType subscriptionType;
    private int bonusAiReadings;
    private boolean proActive;

    public UserProfileDto(Long id, String email, String displayName,
                          SubscriptionType subscriptionType, int bonusAiReadings, boolean proActive) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.subscriptionType = subscriptionType;
        this.bonusAiReadings = bonusAiReadings;
        this.proActive = proActive;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public int getBonusAiReadings() {
        return bonusAiReadings;
    }

    public boolean isProActive() {
        return proActive;
    }
}
