package com.java401d18.roomEase.configs;
import com.java401d18.roomEase.models.AppUser;
import com.java401d18.roomEase.repositories.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    AppUserRepository appUserRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        AppUser appUser = appUserRepository.findByUsername(username);
//
//        if (appUser == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        return (UserDetails) appUser;
//    }

    @Service
    public class UserDetailsServiceImpl implements UserDetailsService {

        @Autowired
        private AppUserRepository applicationUserRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            AppUser user = applicationUserRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException(username);
            }
            return (UserDetails) user;
        }
}




