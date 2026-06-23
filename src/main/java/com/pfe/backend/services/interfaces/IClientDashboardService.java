package com.pfe.backend.services.interfaces;

import com.pfe.backend.dtos.StatistiquesResponseDto;
import com.pfe.backend.exceptions.ClientNotFoundException;

public interface IClientDashboardService {
    StatistiquesResponseDto getStats(Long ClientId) throws ClientNotFoundException;
}
