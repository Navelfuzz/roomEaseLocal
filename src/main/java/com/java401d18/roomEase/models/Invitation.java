package com.java401d18.roomEase.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AppUser fromUser;

    @ManyToOne
    private AppUser toUser;

    @ManyToOne
    private Household household;

    // Constructors
    public Invitation() {
    }

    public Invitation(AppUser fromUser, AppUser toUser, Household household) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.household = household;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getFromUser() {
        return fromUser;
    }

    public void setFromUser(AppUser fromUser) {
        this.fromUser = fromUser;
    }

    public AppUser getToUser() {
        return toUser;
    }

    public void setToUser(AppUser toUser) {
        this.toUser = toUser;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }
}
