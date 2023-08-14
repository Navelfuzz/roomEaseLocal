package com.java401d18.roomEase.controllers;

import com.java401d18.roomEase.models.AppUser;
import com.java401d18.roomEase.repositories.AppUserRepository;
import com.java401d18.roomEase.repositories.HouseholdRepository;
import com.java401d18.roomEase.repositories.InvitationRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;




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

    public void authWithHttpServletRequest(String username, String password){
        try{
            request.login(username, password);
        } catch (ServletException e){
            System.out.println("Error authenticating user with servlet");
            e.printStackTrace();
        }
    }

    @PostMapping("/registration")
    public String registerUser(String firstName, String lastName, String username, String password, String email, String phoneNumber, Boolean isAdmin, String telephoneNumber, Model model){
        AppUser existingUser = appUserRepository.findByUsername(username);
        if (existingUser != null){
            model.addAttribute("error", "Username already exists");
            return "registration";
        }

        AppUser appUser = new AppUser(firstName, lastName, username, email, phoneNumber, isAdmin, telephoneNumber);
        String encryptedPassword = passwordEncoder.encode(password);
        appUser.setPassword(encryptedPassword);
        appUserRepository.save(appUser);

        authWithHttpServletRequest(username, password);
        return "redirect:/profile";
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