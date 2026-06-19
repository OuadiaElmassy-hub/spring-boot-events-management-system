package com.pfe.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nom;

    @Column(length = 500, nullable = false,columnDefinition = "text")
    private String description;

    /** URL publique de l'icône stockée (S3 ou /uploads) */
    @Column(name = "icon_url")
    private String iconUrl;

    /** Couleur hex d'accentuation ex: "#6366f1" */
    @Column(length = 20)
    private String couleur;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evenement> evenements;

    public Categorie(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }
}
