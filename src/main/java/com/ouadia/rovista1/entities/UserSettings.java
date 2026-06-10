package com.ouadia.rovista1.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Client client;

    // Notifications
    private boolean bookingConfirmed   = true;
    private boolean eventReminders     = true;
    private boolean newRecommendations = false;
    private boolean promotions         = false;

    // Privacy
    private boolean publicProfile    = true;
    private boolean analyticsSharing = false;
}