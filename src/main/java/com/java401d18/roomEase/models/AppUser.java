package com.java401d18.roomEase.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private boolean isAdmin;
    private String telephoneNumber;

    // Constructors
    public AppUser() {
    }

    public AppUser(String firstName, String lastName, String username, String password, String email, boolean isAdmin, String telephoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
        this.telephoneNumber = telephoneNumber;
    }

//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<Role> roles;

    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;

//    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
//    private List<Invitation> receivedInvitations;


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean approved) {
        this.isApproved = approved;
    }

    //    public Set<Role> getRoles() {
//        return roles;
//    }

//    public List<Household> getHouseholds() {
//        return households;
//    }
//
//    public void setHouseholds(List<Household> households) {
//        this.households = households;
//    }

//    public List<Invitation> getReceivedInvitations() {
//        return receivedInvitations;
//    }
//
//    public void setReceivedInvitations(List<Invitation> receivedInvitations) {
//        this.receivedInvitations = receivedInvitations;
//    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());
    }


}
