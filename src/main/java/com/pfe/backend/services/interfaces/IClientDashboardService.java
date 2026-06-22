package com.pfe.backend.services.interfaces;

import com.pfe.backend.dtos.StatistiquesResponseDto;

public interface IClientDashboardService {
    StatistiquesResponseDto getStats(Long ClientId);
}
