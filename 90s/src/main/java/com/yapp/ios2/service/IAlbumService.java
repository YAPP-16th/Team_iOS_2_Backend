package com.yapp.ios2.service;

import com.yapp.ios2.vo.Album;

import java.util.List;
import java.util.Map;

public interface IAlbumService {
    Album create(String name, Integer photoLimit, Long user, Long layoutUid);
    List<Album> getAlbums(Long userUid);
}
