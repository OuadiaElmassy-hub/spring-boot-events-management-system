package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {

    Optional<UserSettings> findByClientId(Long clientId);
}