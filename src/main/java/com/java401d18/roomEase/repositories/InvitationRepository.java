package com.java401d18.roomEase.repositories;

import com.java401d18.roomEase.models.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}