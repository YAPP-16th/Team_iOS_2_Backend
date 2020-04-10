package com.yapp.ios2.service;

import com.yapp.ios2.repository.AlbumOwnerRepository;
import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AlbumService implements IAlbumService{

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    AlbumOwnerRepository albumOwnerRepository;

    @Override
    public Album create(String name, Integer photoLimit, Long user, Long layoutUid) {

        Album newAlbum = Album.builder()
                .name(name)
                .photoLimit(photoLimit)
                .layoutUid(layoutUid)
                .build();

        albumRepository.save(newAlbum);

        AlbumOwner albumOwner = AlbumOwner.builder()
                .albumUid(newAlbum.getUid())
                .userUid(user)
                .role("creator")
                .build();

        albumOwnerRepository.save(albumOwner);

        return newAlbum;
    }

    @Override
    public List<Album> getAlbums(Long userUid) {

        List<AlbumOwner> albumOwners = albumOwnerRepository.findByUserUid(userUid);
        List<Album> albums = new ArrayList<>();

        for(AlbumOwner owner : albumOwners){
            albums.add(
                    albumRepository.findById(owner.getAlbumUid()).get()
            );
        }

        return albums;
    }
}
