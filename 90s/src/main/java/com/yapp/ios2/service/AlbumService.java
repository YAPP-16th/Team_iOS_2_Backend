package com.yapp.ios2.service;

import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.dto.AlbumOwnerDto;
import com.yapp.ios2.repository.*;
import com.yapp.ios2.config.exception.AlbumNotFoundException;
import com.yapp.ios2.repository.AlbumOwnerRepository;
import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.vo.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AlbumService{

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    AlbumOrderRepository albumOrderRepository;

    @Autowired
    AlbumOrderPaperType1Repository albumOrderPaper1TypeRepository;
    @Autowired
    AlbumOrderPaperType2Repository albumOrderPaper2TypeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlbumOwnerRepository albumOwnerRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    AlbumOrderPostTypeRepository albumOrderPostTypeRepository;

    @Autowired
    AlbumOrderStatusRepository albumOrderStatusRepository;

    @Autowired
    S3Service s3Service;

    @Autowired
    CoverRepository coverRepository;

    public Album create(String name, Integer photoLimit, Long user, Long layoutUid, Long cover, LocalDate endDate) {

        Album newAlbum = Album.builder()
                .name(name)
                .password(UUID.randomUUID())
                .photoLimit(photoLimit)
                .layoutUid(layoutUid)
                .cover(coverRepository.findById(cover).get())
                .endDate(endDate)
                .orderStatus(albumOrderStatusRepository.findById(1L).get())
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

    public void removeOwner(Long albumUid, Long user){

        albumOwnerRepository.delete(
                albumOwnerRepository.findByAlbumAndUser(
                        albumRepository.findById(albumUid).get(),
                        userRepository.findById(user).get()
                )
        );

    }

    public void removeAlbum(Album album){

        // Delete all photos in S3
        s3Service.deleteByAlbum(album.getUid());

        albumRepository.delete(album);


    }


    public Album getAlbum(Long albumUid) {

        Album album = albumRepository.findById(albumUid)
                .orElseThrow(() -> new IllegalArgumentException("Not exist album"));

        return album;
    }

    public List<Album> getAlbumsNotReady(User user){
        List<Album> albums = albumRepository.findByOrderStatus(user, albumOrderStatusRepository.findById(1L).get());
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
                .paperType1(albumOrderPaper1TypeRepository.findById(albumOrderInfo.getPaperType1()).get())
                .paperType2(albumOrderPaper2TypeRepository.findById(albumOrderInfo.getPaperType2()).get())
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



    public List<AlbumOwnerDto.AlbumOwnerInfo> getAlbumOwners(Long albumUid){

        List<User> owners = userRepository.findUsersByAlbum(albumRepository.findById(albumUid).get());

        List<AlbumOwner> albumOwners = albumOwnerRepository.findByAlbumUid(albumUid);

        List<AlbumOwnerDto.AlbumOwnerInfo> albumOwnerInfos = AlbumOwnerDto.convertFromAlbumOwnerListToAlbumOwnerInfoList(albumOwners);

        return albumOwnerInfos;

    }



    public void plusCount(Long albumUid){
        Album album = albumRepository.findById(albumUid).
                orElseThrow(() -> new AlbumNotFoundException());
        Integer count = album.getCount();
        album.setCount(++count);
        albumRepository.save(album);
    }


    public void completeChecker(Long albumUid){
        Album album = albumRepository.findById(albumUid).get();
        if(!album.isComplete()){
            if(photoRepository.findByAlbum(album).size() >= album.getPhotoLimit()){
                album.setComplete(true);
                albumRepository.save(album);
            }else if(album.getEndDate().isBefore(ChronoLocalDate.from(LocalDate.now()))){
                album.setComplete(true);
                albumRepository.save(album);
            }
        }
    }

    public String invite(Long albumUid, Long userUid){

        String randomNum = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));



        return randomNum;
    }

}
