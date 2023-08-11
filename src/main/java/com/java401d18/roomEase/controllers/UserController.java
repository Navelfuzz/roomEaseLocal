package com.java401d18.roomEase.controllers;

import com.java401d18.roomEase.models.AppUser;
import com.java401d18.roomEase.repositories.UserRepository;
import com.java401d18.roomEase.models.Household;
import com.java401d18.roomEase.repositories.HouseholdRepository;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HouseholdRepository householdRepository;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    public RedirectView postRegistration(@RequestParam String firstName,
                                         @RequestParam String lastName,
                                         @RequestParam String username,
                                         @RequestParam String password,
                                         @RequestParam String email,
                                         @RequestParam String telephoneNumber,
                                         @RequestParam(required = false) boolean isAdmin) {
        AppUser user = new AppUser(firstName, lastName, username, passwordEncoder.encode(password), email, isAdmin, telephoneNumber);
        userRepository.save(user);

        // Redirect to login page
        return new RedirectView("/login");
    }

    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {
        String username = principal.getName();
        AppUser user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

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
        AppUser admin = userRepository.findByUsername(username);
        String householdId = generateHouseholdId();

        Household household = new Household(admin, householdId, streetNumber, streetName, city, state, zip, maxOccupants);
        householdRepository.save(household);

        // Add admin to household members
        household.getMembers().add(admin);
        admin.getHouseholds().add(household);
        userRepository.save(admin);

        return new RedirectView("/profile");
    }

    // Add more methods for other functionalities...
}
