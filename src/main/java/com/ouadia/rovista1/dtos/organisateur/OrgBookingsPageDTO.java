package com.ouadia.rovista1.dtos.organisateur;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class OrgBookingsPageDTO {
    
    List<OrgBookingDTO> content;
    int totalPages;
    long totalElements;
    int number;
    Double totalRevenu;
}