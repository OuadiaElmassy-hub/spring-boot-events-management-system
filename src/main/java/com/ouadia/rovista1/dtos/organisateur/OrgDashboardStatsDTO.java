package com.ouadia.rovista1.dtos.organisateur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrgDashboardStatsDTO {

    String organisationNom;
    long   activeEvents;
    long   newEventsThisMonth;
    long   totalParticipants;
    long   newParticipantsThisWeek;
    Double totalRevenue;
    Double revenueGrowth;          // % vs mois dernier
    Double avgFillRate;
}