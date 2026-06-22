package com.pfe.backend.services.client;

import com.pfe.backend.dtos.SettingsDTO;
import com.pfe.backend.dtos.UpdateSettingRequest;
import com.pfe.backend.entities.Client;
import com.pfe.backend.entities.UserSettings;
import com.pfe.backend.repositories.ClientRepository;
import com.pfe.backend.repositories.UserSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ClientSettingsService {

    private final UserSettingsRepository settingsRepo;
    private final ClientRepository userRepo;

    public SettingsDTO getSettings(Long userId) {
        UserSettings s = getOrCreate(userId);

        return SettingsDTO.builder()
                .notifications(SettingsDTO.NotificationsSettings.builder()
                        .bookingConfirmed(s.isBookingConfirmed())
                        .eventReminders(s.isEventReminders())
                        .newRecommendations(s.isNewRecommendations())
                        .promotions(s.isPromotions())
                        .build())
                .privacy(SettingsDTO.PrivacySettings.builder()
                        .publicProfile(s.isPublicProfile())
                        .analyticsSharing(s.isAnalyticsSharing())
                        .build())
                .build();
    }


    @Transactional
    public void updateSetting(Long userId, UpdateSettingRequest req) {
        UserSettings s = getOrCreate(userId);

        switch (req.getSection() + "." + req.getKey()) {
            case "notifications.bookingConfirmed"   -> s.setBookingConfirmed(req.getValue());
            case "notifications.eventReminders"     -> s.setEventReminders(req.getValue());
            case "notifications.newRecommendations" -> s.setNewRecommendations(req.getValue());
            case "notifications.promotions"         -> s.setPromotions(req.getValue());
            case "privacy.publicProfile"            -> s.setPublicProfile(req.getValue());
            case "privacy.analyticsSharing"         -> s.setAnalyticsSharing(req.getValue());
            default -> throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Paramètre inconnu : " + req.getKey());
        }
        settingsRepo.save(s);
    }

    private UserSettings getOrCreate(Long userId) {
        return settingsRepo.findByClientId(userId).orElseGet(() -> {
            Client u = userRepo.findById(userId).orElseThrow();
            UserSettings s = new UserSettings();
            s.setClient(u);
            return settingsRepo.save(s);
        });
    }
}