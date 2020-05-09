package com.yapp.ios2.repository;

import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOwner;
import com.yapp.ios2.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumOwnerRepository extends JpaRepository<AlbumOwner, Long> {
    List<AlbumOwner> findByUserUid(Long userUid);
    List<AlbumOwner> findByAlbumUid(Long albumUid);
    List<User> findUserByAlbum(Album album);
}
