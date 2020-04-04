package com.yapp.ios2.repository;

import com.yapp.ios2.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
