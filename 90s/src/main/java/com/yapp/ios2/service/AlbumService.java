package com.yapp.ios2.service;

import com.yapp.ios2.repository.AlbumOwnerRepository;
import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOwner;
import com.yapp.ios2.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AlbumService{

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlbumOwnerRepository albumOwnerRepository;

    public Album create(String name, Integer photoLimit, Long user, Long layoutUid, LocalDate endDate) {

        Album newAlbum = Album.builder()
                .name(name)
                .photoLimit(photoLimit)
                .layoutUid(layoutUid)
                .endDate(endDate)
                .count(0)
                .build();

        albumRepository.save(newAlbum);

        addOwner(newAlbum.getUid(), user, "ROLE_CREATOR");

        return newAlbum;
    }

    public AlbumOwner addOwner(Long albumUid, Long user, String role){

        AlbumOwner albumOwner = AlbumOwner.builder()
                .albumUid(albumUid)
                .userUid(user)
                .role(role)
                .build();

        albumOwnerRepository.save(albumOwner);

        return albumOwner;
    }

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

    public List<User> getAlbumOwners(Long albumUid){

        List<AlbumOwner> albumOwners = albumOwnerRepository.findByAlbumUid(albumUid);

        List<User> owners = new ArrayList<>();

        for(AlbumOwner owner : albumOwners){
            owners.add(
                    userRepository.findById(owner.getUserUid()).get()
            );
        }

        return owners;

    }
}
