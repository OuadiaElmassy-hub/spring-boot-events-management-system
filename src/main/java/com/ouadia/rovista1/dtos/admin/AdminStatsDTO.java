package com.ouadia.rovista1.dtos.admin;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdminStatsDTO{
    private long totalUsers;
    private long totalOrganizers;
    private long activeEvents;
    private long pendingEvents;
    private long totalBookings;
    private long newUsersThisMonth;
    private long bookingsThisMonth;
    private long pendingOrganizers;
    private Double totalRevenue;
    private Double revenueGrowth;      // pourcentage vs mois dernier (optionnel)
}
