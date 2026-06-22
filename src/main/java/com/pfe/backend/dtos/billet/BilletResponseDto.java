package com.pfe.backend.dtos.billet;

import com.pfe.backend.entities.enums.TypeBillet;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter// pour les methode getter, setter, toString() , hachcode() ,equals()
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BilletResponseDto {

    private Long id;
    private String code;
    private String qrCode;
    private LocalDateTime dateBillet;
    @Enumerated(EnumType.STRING)
    private TypeBillet type;

}
