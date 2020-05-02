package com.yapp.ios2.repository;

import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumOrderRepository  extends JpaRepository<AlbumOrder, Long> {
    Optional<AlbumOrder> findByAlbum(Album album);
}
