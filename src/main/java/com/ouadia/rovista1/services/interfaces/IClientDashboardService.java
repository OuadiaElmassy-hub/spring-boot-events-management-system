package com.ouadia.rovista1.services.interfaces;

import com.ouadia.rovista1.dtos.StatistiquesResponseDto;

public interface IClientDashboardService {
    StatistiquesResponseDto getStats(Long ClientId);
}
