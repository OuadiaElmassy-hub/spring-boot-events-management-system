package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.TypeMessage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
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


}
