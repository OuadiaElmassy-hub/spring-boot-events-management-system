package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.TypeMessage;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime dateEnvoi;
    @Enumerated(EnumType.STRING)
    private TypeMessage typeMessage;

    @ManyToOne
    private Utilisateur destinataire;

    public Notification() {
    }

    public Notification(Long id, String content, LocalDateTime dateEnvoi,
                        TypeMessage typeMessage, Utilisateur destinataire) {
        this.id = id;
        this.content = content;
        this.dateEnvoi = dateEnvoi;
        this.typeMessage = typeMessage;
        this.destinataire = destinataire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
    }

    public Utilisateur getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Utilisateur destinataire) {
        this.destinataire = destinataire;
    }
}
