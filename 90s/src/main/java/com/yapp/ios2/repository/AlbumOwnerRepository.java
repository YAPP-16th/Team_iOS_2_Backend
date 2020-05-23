package com.yapp.ios2.repository;

import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOwner;
import com.yapp.ios2.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlbumOwnerRepository extends JpaRepository<AlbumOwner, Long> {
    List<AlbumOwner> findByUserUid(Long userUid);
    List<AlbumOwner> findByAlbumUid(Long albumUid);
    @Query("select ao from AlbumOwner ao where ao.album = :album and ao.role not like :role")
    List<AlbumOwner> findByAlbumUidandRole(@Param("album") Long albumUid,@Param("role")  String role);
    List<User> findUserByAlbum(Album album);
    AlbumOwner findByAlbumAndUser(Album album, User user);

}
