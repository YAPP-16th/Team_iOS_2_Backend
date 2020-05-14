package com.yapp.ios2.repository;

import com.yapp.ios2.vo.NoticeAgreement;
import com.yapp.ios2.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoticeAgreementRepository extends JpaRepository<NoticeAgreement, Long> {
    NoticeAgreement findByUser(User user);
}
