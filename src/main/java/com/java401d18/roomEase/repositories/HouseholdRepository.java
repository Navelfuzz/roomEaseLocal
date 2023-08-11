package com.java401d18.roomEase.repositories;

import com.java401d18.roomEase.models.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Set;

public interface HouseholdRepository extends JpaRepository<Household, Long> {
    Household findByHouseholdId(String householdId);

    List<Household> findByMembersId(Long memberId);
}
