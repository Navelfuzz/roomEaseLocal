package com.java401d18.roomEase.repositories;

import com.java401d18.roomEase.models.AppUser;
import com.java401d18.roomEase.models.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    List<AppUser> findByHousehold(Household household);



}
