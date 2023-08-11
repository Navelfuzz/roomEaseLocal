package com.java401d18.roomEase.repositories;

import com.java401d18.roomEase.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    List<AppUser> findByHouseholdId(Long householdId);
}
