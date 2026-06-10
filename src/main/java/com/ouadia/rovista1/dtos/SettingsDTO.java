package com.ouadia.rovista1.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
@Getter
@Setter
@Builder
@Value
public class SettingsDTO {
    NotificationsSettings notifications;
    PrivacySettings privacy;

    @Getter
    @Setter
    @Builder
    @Value
    public static class NotificationsSettings {
        boolean bookingConfirmed;
        boolean eventReminders;
        boolean newRecommendations;
        boolean promotions;
    }

    @Getter
    @Setter
    @Builder
    @Value
    public static class PrivacySettings {
        boolean publicProfile;
        boolean analyticsSharing;
    }
}
