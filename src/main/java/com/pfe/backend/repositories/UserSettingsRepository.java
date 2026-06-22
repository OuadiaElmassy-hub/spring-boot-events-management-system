package com.pfe.backend.repositories;

import com.pfe.backend.entities.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {

    Optional<UserSettings> findByClientId(Long clientId);
}