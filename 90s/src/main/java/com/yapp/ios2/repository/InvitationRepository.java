package com.yapp.ios2.repository;

import com.yapp.ios2.vo.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
