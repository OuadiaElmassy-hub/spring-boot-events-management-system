package com.pfe.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "favorie",
        uniqueConstraints = @UniqueConstraint(columnNames = {"client_id", "evenement_id"}))

public class Favorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(nullable = false,columnDefinition = "text")
    private String description;
    @Column(nullable = false)
    private LocalDateTime dateCreation;

    @ManyToOne(fetch = FetchType.LAZY)//, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)//, cascade = CascadeType.ALL)
    @JoinColumn(name = "evenement_id", nullable = false)
    private Evenement evenement;

    @PrePersist void onCreate() { this.dateCreation = LocalDateTime.now(); }
}
