package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.TypePhoto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(nullable = false)
    private  String nom;
    @Column(nullable = false)
    private  String url;
    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
    private String type;

    @ManyToOne
    private Evenement evenement;
}
