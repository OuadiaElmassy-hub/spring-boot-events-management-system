package com.pfe.backend.dtos.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
@Builder
public class AdminDetailedStatsDTO{
    long totalBookings;
    Double avgOccupancyRate;
    Double avgRevenuePerEvent;
    long eventsThisMonth;
    Map<String, Double> revenueByCategory;
    Map<String, Long>    eventsByCategory;
    List<VilleStatDTO> topCities;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class VilleStatDTO{
        String name;
        long count;
    }
}