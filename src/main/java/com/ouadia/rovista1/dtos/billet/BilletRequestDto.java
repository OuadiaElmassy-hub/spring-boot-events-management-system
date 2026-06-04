package com.ouadia.rovista1.dtos.billet;

import com.ouadia.rovista1.entities.Reservation;
import com.ouadia.rovista1.entities.enums.TypeBillet;
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
