package com.yapp.ios2.service;

import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.repository.*;
import com.yapp.ios2.config.exception.AlbumNotFoundException;
import com.yapp.ios2.repository.AlbumOwnerRepository;
import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOrder;
import com.yapp.ios2.vo.AlbumOwner;
import com.yapp.ios2.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService{

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    AlbumOrderRepository albumOrderRepository;

    @Autowired
    AlbumOrderPaperTypeRepository albumOrderPaperTypeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlbumOwnerRepository albumOwnerRepository;

    @Autowired
    AlbumOrderPostTypeRepository albumOrderPostTypeRepository;

    @Autowired
    AlbumOrderStatusRepository albumOrderStatusRepository;

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
                .album(albumRepository.findById(albumUid).get())
                .user(userRepository.findById(user).get())
                .role(role)
                .build();

        albumOwnerRepository.save(albumOwner);

        return albumOwner;
    }

    public Album getAlbum(Long albumUid) {

        Album album = albumRepository.findById(albumUid)
                .orElseThrow(() -> new IllegalArgumentException("Not exist album"));

        return album;
    }

    public List<Album> getAlbumsNotReady(User user){
        List<Album> albums = albumRepository.findByOrderStatus(user, "ready");
        return albums;


    }

    public List<Album> getAlbumsByUser(User user) {

        List<Album> albums = albumRepository.findByUser(user);

        return albums;
    }

    public AlbumOrder createAlbumOrder(AlbumDto.AlbumOrderInfo albumOrderInfo){

        AlbumOrder newAlbumOrder = AlbumOrder.builder()
                .cost(albumOrderInfo.getCost())
                .recipient(albumOrderInfo.getRecipient())
                .postalCode(albumOrderInfo.getPostalCode())
                .address(albumOrderInfo.getAddress())
                .addressDetail(albumOrderInfo.getAddressDetail())
                .phoneNum(albumOrderInfo.getPhoneNum())
                .message(albumOrderInfo.getMessage())
                .paperType(albumOrderPaperTypeRepository.findById(albumOrderInfo.getPaperType()).get())
                .postType(albumOrderPostTypeRepository.findById(albumOrderInfo.getPostType()).get())
                .build();

        albumOrderRepository.save(newAlbumOrder);

        return newAlbumOrder;
    }

    public void changeAlbumOrderStatus(Long albumUid, boolean status) {

        AlbumOrder albumOrder = albumOrderRepository.findByAlbum(albumRepository.findById(albumUid).get())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        if (status) {
//            forward
            albumOrder.setStatus(
                    albumOrderStatusRepository.findById(
                            albumOrder.getStatus().getUid() + 1
                    ).orElseThrow(() -> new IllegalArgumentException("이미 완료된 주문건입니다."))
            );

        } else {
//            backward
            albumOrder.setStatus(
                    albumOrderStatusRepository.findById(
                            albumOrder.getStatus().getUid() - 1
                    ).orElseThrow(() -> new IllegalArgumentException("이미 입금 대기 주문건입니다."))
            );
        }
    }



    public List<User> getAlbumOwners(Long albumUid){

        List<AlbumOwner> albumOwners = albumOwnerRepository.findByAlbumUid(albumUid);

        List<User> owners = new ArrayList<>();

        for(AlbumOwner owner : albumOwners){
            owners.add(
                    owner.getUser()
            );
        }

        return owners;

    }



    public void plusCount(Long albumUid){
        Album album = albumRepository.findById(albumUid).
                orElseThrow(() -> new AlbumNotFoundException());
        Integer count = album.getCount();
        album.setCount(++count);
        albumRepository.save(album);
    }


}
