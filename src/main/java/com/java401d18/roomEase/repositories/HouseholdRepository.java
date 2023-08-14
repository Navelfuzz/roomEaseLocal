package com.java401d18.roomEase.repositories;

import com.java401d18.roomEase.models.Household;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdRepository extends JpaRepository<Household, Long> {
    Household findByHouseholdId(Long householdId);

}
