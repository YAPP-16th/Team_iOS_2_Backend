package com.yapp.ios2.repository;

import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u, AlbumOwner ao where ao.album = :album and ao.user = u")
    List<User> findUsersByAlbum(@Param("album") Album album);
    Optional<User> findUserByPhone(String phone);
}
