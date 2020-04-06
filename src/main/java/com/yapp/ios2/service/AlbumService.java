package com.yapp.ios2.service;

import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.vo.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
class AlbumService implements IAlbumService{

    @Autowired
    AlbumRepository albumRepository;

    @Override
    public Album create(String name, Integer photoLimit) {

        Album newAlbum = Album.builder()
                .name(name)
                .photoLimit(photoLimit)
                .build();

        albumRepository.save(newAlbum);

        return newAlbum;
    }
}
