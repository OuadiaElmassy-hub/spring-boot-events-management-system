package com.pfe.backend.dtos.billet;

import com.pfe.backend.entities.enums.TypeBillet;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BilletRequestDto {

    private String code;
    private String qrCode;
    private LocalDateTime dateBillet;
    @Enumerated(EnumType.STRING)
    private TypeBillet type;
    private Long reservationId;

}
