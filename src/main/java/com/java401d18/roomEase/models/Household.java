package com.java401d18.roomEase.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Household {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long householdId;
    private String streetNumber;
    private String streetName;
    private String city;
    private String state;
    private String zip;
    private int maxOccupants;
    private int currentOccupants;

    // Constructors
    @OneToMany(mappedBy = "household")
    private List<AppUser> users;

    public Household() {
    }

    public Household(Long householdId, String streetNumber, String streetName, String city, String state, String zip, int maxOccupants, List<AppUser> users) {
        this.householdId = householdId;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.maxOccupants = maxOccupants;
        this.currentOccupants = 0; // Assuming newly created household has no occupants initially
        this.users = users;
    }

    // Getters and Setters
    public Long getHouseholdId() {
        return householdId;
    }
    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public int getMaxOccupants() {
        return maxOccupants;
    }

    public void setMaxOccupants(int maxOccupants) {
        this.maxOccupants = maxOccupants;
    }

    public int getCurrentOccupants() {
        return currentOccupants;
    }

    public void setCurrentOccupants(int currentOccupants) {
        this.currentOccupants = currentOccupants;
    }

    public List<AppUser> getUsers() {
        return users;
    }

    public void setUsers(List<AppUser> users) {
        this.users = users;
    }

}
