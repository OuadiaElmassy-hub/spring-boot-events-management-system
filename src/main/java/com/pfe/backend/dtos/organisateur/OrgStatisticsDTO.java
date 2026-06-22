package com.pfe.backend.dtos.organisateur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrgStatisticsDTO {
    private Double averageFillRate;
    Double              totalRevenue;
    long                totalParticipants;
    Long              avgFillRate;
    long                activeEvents;
    List<RevenueItem> revenueByEvent;
    List<FillRateItem>  fillRateByEvent;
    List<MonthItem>     bookingsByMonth;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RevenueItem {
        String titre;
        Double revenus;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class FillRateItem {
        String titre;
        int participants;
        int capacite;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class MonthItem {
        String month;
        long count;
    }
}
