package com.ouadia.rovista1.repositories;

import com.ouadia.rovista1.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser finfByUsername(String username);
}
