package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.TypeMessage;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;
    private boolean estLu = false;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private TypeMessage typeMessage; // "EVENT_SUBMITTED", "USER_REGISTERED", etc.

    @ManyToOne(fetch = FetchType.LAZY)
    private Utilisateur destinataire;

    @PrePersist void onCreate() { this.createdAt = LocalDateTime.now(); }
}
