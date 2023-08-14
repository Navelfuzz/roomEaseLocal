package com.java401d18.roomEase.controllers;

import com.java401d18.roomEase.models.AppUser;
import com.java401d18.roomEase.models.Household;
import com.java401d18.roomEase.repositories.AppUserRepository;
import com.java401d18.roomEase.repositories.HouseholdRepository;
import com.java401d18.roomEase.repositories.InvitationRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/")
    String getHomePage() {
        return "index";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    public RedirectView postRegistration(String firstName, String lastName, String username, String password, String email, Boolean isAdmin, String telephoneNumber, Long householdId) {
        AppUser user = new AppUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setTelephoneNumber(telephoneNumber);

        Household selectedHousehold = householdRepository.findById(householdId).orElse(null);
        List<AppUser> householdMembers = appUserRepository.findByHousehold(selectedHousehold);

        if(householdMembers == null || householdMembers.isEmpty()){
            user.setAdmin(true);
            user.setIsApproved(true);
        } else {
            user.setAdmin(false);
            user.setIsApproved(false);
        }
        user.setHousehold(selectedHousehold);
        appUserRepository.save(user);

        return new RedirectView("/login");
    }


    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {
        String username = principal.getName();
        AppUser user = appUserRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

//    @GetMapping("/admin")
//    public String getAdministrationDash(Model model, Principal principal){
//        if(principal != null){
//            String username = principal.getName();
//            AppUser currentUser = appUserRepository.findByUsername(username);
//
//            if(currentUser.isAdmin()) {
//                Household userHousehold = currentUser.getHouseholds();
//                List<AppUser> nonApprovedUsers = appUserRepository.findNonApprovedUsersByHousehold(userHousehold);
//                List<AppUser> nonAdminUsers = appUserRepository.findNonAdminUsersByHousehold(userHousehold);
//
//                model.addAttribute("nonApprovedUsers", nonApprovedUsers);
//                model.addAttribute("nonAdminUsers", nonAdminUsers);
//
//                return "admin";
//            } else {
//                return "redirect:/profile";
//            }
//        }
//    }

}