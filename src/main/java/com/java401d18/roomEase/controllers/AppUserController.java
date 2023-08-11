package com.java401d18.roomEase.controllers;

import com.java401d18.roomEase.models.AppUser;
import com.java401d18.roomEase.repositories.AppUserRepository;
import com.java401d18.roomEase.models.Household;
import com.java401d18.roomEase.repositories.HouseholdRepository;
import com.java401d18.roomEase.models.Invitation;
import com.java401d18.roomEase.repositories.InvitationRepository;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;
import java.util.List;


@Controller
public class AppUserController {

    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HouseholdRepository householdRepository;
    @Autowired
    InvitationRepository invitationRepository;


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
        appUserRepository.save(user);

        // Redirect to login page
        return new RedirectView("/login");
    }

    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {
        String username = principal.getName();
        AppUser user = appUserRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

    // Add more methods for other functionalities...
    @PostMapping("/send-invitation")
    public String sendInvitation(@RequestParam Long fromUserId, @RequestParam Long toUserId, @RequestParam Long householdId) {
        AppUser fromUser = appUserRepository.findById(fromUserId).orElse(null);
        AppUser toUser = appUserRepository.findById(toUserId).orElse(null);
        Household household = householdRepository.findById(householdId).orElse(null);

        if (fromUser != null && toUser != null && household != null) {
            Invitation invitation = new Invitation(fromUser, toUser, household);
            toUser.getReceivedInvitations().add(invitation);
            appUserRepository.save(toUser);
        }

        return "redirect:/profile"; // Redirect to user's profile page
    }

    @PostMapping("/accept-invitation")
    public String acceptInvitation(@RequestParam Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId).orElse(null);

        if (invitation != null) {
            // Handle updating user's household or any other related logic
            invitationRepository.delete(invitation);
        }

        return "redirect:/profile"; // Redirect to user's profile page
    }

//    @PostMapping("/reject-invitation")
//    public String rejectInvitation(@RequestParam Long invitationId) {
//        Invitation invitation = invitationRepository.findById(invitationId).orElse(null);
//
//        if (invitation != null) {
//            invitationRepository.delete(invitation); // Remove the invitation from user's receivedInvitations
//        }
//
//        return "redirect:/profile"; // Redirect to user's profile page
//    }
    @GetMapping("/administrationDash")
    public String getAdministrationDash(Model model, Principal principal){
        if(principal != null){
            String username = principal.getName();
            AppUser currentUser = appUserRepository.findByUsername(username);

            if(currentUser.isAdmin()) {
                Household userHousehold = currentUser.getHouseholds();
                List<AppUser> nonApprovedUsers = appUserRepository.findNonApprovedUsersByHousehold(userHousehold);
                List<AppUser> nonAdminUsers = appUserRepository.findNonAdminUsersByHousehold(userHousehold);

                model.addAttribute("nonApprovedUsers", nonApprovedUsers);
                model.addAttribute("nonAdminUsers", nonAdminUsers);

                return "administrationDash";
            } else {
                return "redirect:/profile";
            }
        }
    }

}