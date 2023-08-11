package com.java401d18.roomEase.controllers;

import com.java401d18.roomEase.models.Household;
import com.java401d18.roomEase.models.AppUser;
import com.java401d18.roomEase.repositories.HouseholdRepository;
import com.java401d18.roomEase.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class HouseholdController {

    @Autowired
    HouseholdRepository householdRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/household/{id}")
    public String getHousehold(@PathVariable Long id, Model model) {
        Household household = householdRepository.findById(id).orElse(null);

        if (household == null) {
            // Handle the case when household is not found
            return "error"; // Create an error.html template or redirect as needed
        }

        model.addAttribute("household", household);
        return "household";
    }

    @PostMapping("/join-household")
    public RedirectView joinHousehold(@RequestParam String householdId, Principal principal) {
        String username = principal.getName();
        AppUser user = appUserRepository.findByUsername(username);

        Household household = householdRepository.findByHouseholdId(householdId);

        if (household != null) {
            household.getMembers().add(user);
            user.getHouseholds().add(household);
            appUserRepository.save(user);
        }

        return new RedirectView("/profile");
    }

    // Add more methods for household-related functionalities...
    @GetMapping("/create-household")
    public String getCreateHousehold() {
        return "create-household";
    }

    // Method to generate a random household ID
    private String generateHouseholdId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = 8;
        StringBuilder householdId = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            householdId.append(characters.charAt(randomIndex));
        }

        return householdId.toString();
    }

    @PostMapping("/create-household")
    public RedirectView postCreateHousehold(@RequestParam String streetNumber,
                                            @RequestParam String streetName,
                                            @RequestParam String city,
                                            @RequestParam String state,
                                            @RequestParam String zip,
                                            @RequestParam int maxOccupants,
                                            Principal principal) {
        String username = principal.getName();
        AppUser admin = appUserRepository.findByUsername(username);
        String householdId = generateHouseholdId();

        Household household = new Household(admin, householdId, streetNumber, streetName, city, state, zip, maxOccupants);
        householdRepository.save(household);

        // Add admin to household members
        household.getMembers().add(admin);
        admin.getHouseholds().add(household);
        appUserRepository.save(admin);

        return new RedirectView("/profile");
    }
}
