package com.yapp.ios2.repository;

import com.yapp.ios2.vo.AlbumOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumOwnerRepository extends JpaRepository<AlbumOwner, Long> {
    List<AlbumOwner> findByUserUid(Long userUid);
}
