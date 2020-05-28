package com.yapp.ios2.repository;


import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOrderStatus;
import com.yapp.ios2.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long>{
    @Query("select a from Album a, AlbumOwner ao where ao.user = :user and a = ao.album")
    List<Album> findByUser(@Param("user")User user);

    @Query("select a from Album a, AlbumOwner ao where ao.user = :user and a= ao.album and a.orderStatus not like :orderStatus")
    List<Album> findByOrderStatus(@Param("user") User user, @Param("orderStatus") AlbumOrderStatus orderStatus);

}
