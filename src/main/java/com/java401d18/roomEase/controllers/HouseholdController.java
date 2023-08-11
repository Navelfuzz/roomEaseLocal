package com.java401d18.roomEase.controllers;

import com.java401d18.roomEase.models.Household;
import com.java401d18.roomEase.models.AppUser;
import com.java401d18.roomEase.repositories.HouseholdRepository;
import com.java401d18.roomEase.repositories.UserRepository;
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
    UserRepository userRepository;

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
        AppUser user = userRepository.findByUsername(username);

        Household household = householdRepository.findByHouseholdId(householdId);

        if (household != null) {
            household.getMembers().add(user);
            user.getHouseholds().add(household);
            userRepository.save(user);
        }

        return new RedirectView("/profile");
    }

    // Add more methods for household-related functionalities...
}
