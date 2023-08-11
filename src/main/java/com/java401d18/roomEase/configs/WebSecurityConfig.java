//package com.java401d18.roomEase.configs;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import org.springframework.web.filter.HiddenHttpMethodFilter;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//    @Autowired
//    private UserDetailsServiceImpl siteUserDetailsService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
//        return new HiddenHttpMethodFilter();
//    }
//
//    @Bean
//    protected SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
//        http
//                .cors().disable()
//                .csrf().disable()
//                .authorizeHttpRequests((auth) -> auth
//                        // Your request matcher configurations...
//                        .requestMatchers("/").permitAll()
//                        .requestMatchers("/login").permitAll()
//                        .requestMatchers("/registration").permitAll()
//                        .requestMatchers("/myprofile").authenticated()
//                        .requestMatchers("/administrationdash").hasAuthority("ADMIN")
//                        .requestMatchers("/newhousehold").hasAuthority("ADMIN")
//                        .requestMatchers("/login", "/logout", "/aboutUs").permitAll()
//                        .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/scss/**", "/vendor/**").permitAll()
//                )
//                .formLogin()
//                .loginPage("/login").permitAll()
//                .defaultSuccessUrl("/myprofile")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/") // Set the custom logout success handler
//                .and()
//                .getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(siteUserDetailsService)
//                .passwordEncoder(passwordEncoder());
//
//        return http.build();
//    }
//}
