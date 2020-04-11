package com.yapp.ios2.service;

import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOwner;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IAlbumService {
    Album create(String name, Integer photoLimit, Long user, Long layoutUid, LocalDate endDate);

    List<Album> getAlbums(Long userUid);

    AlbumOwner addOwner(Long albumUid, Long user, String role);
}
