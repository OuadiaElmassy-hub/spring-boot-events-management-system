package com.ouadia.rovista1.entities;

import com.ouadia.rovista1.entities.enums.TypePhoto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data // pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private  Long id;
    @NotEmpty
    private  String nom;
    @NotEmpty
    private  String url;
    @NotEmpty
    private TypePhoto type;
    @ManyToOne
    private List<Evenement> evenement;
}
