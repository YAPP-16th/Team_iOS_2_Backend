package com.yapp.ios2.controller;

import com.amazonaws.services.mq.model.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.config.exception.AlbumNotFoundException;
import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.repository.AlbumOrderRepository;
import com.yapp.ios2.service.AlbumOrderService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOrder;
import com.yapp.ios2.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/album/order/*")
public class AlbumOrderController {

    @Autowired
    AlbumOrderService albumOrderService;

    @Autowired
    AlbumOrderRepository albumOrderRepository;

    @Autowired
    UserService userService;



    @PostMapping("/createAlbumOrder")
    public AlbumOrder createAlbumOrder(@AuthenticationPrincipal UserDetails userDetail, @RequestBody AlbumDto.AlbumOrderInfo albumOrderInfo) throws Exception{
        AlbumOrder newAlbumOrder = albumOrderService.createAlbumOrder(albumOrderInfo, userService.getUserByEmail(userDetail.getUsername()));
        return newAlbumOrder;
    }

    @GetMapping("/getAlbumOrders")
    public List<AlbumOrder> getAlbumOrders(@AuthenticationPrincipal User user) {
        return albumOrderRepository.findAllByUser(user);
    }

    @DeleteMapping("/deleteAlbumOrder/{albumOrderUid}")
    public void deleteAlbumOrder(@PathVariable("albumOrderUid") Long albumOrderUid){
        AlbumOrder albumOrder = albumOrderRepository.findById(albumOrderUid).orElseThrow(
                () -> new AlbumNotFoundException("없는 엘범 인데!")
        );

        albumOrderRepository.delete(albumOrder);
    }


    @PostMapping("/changeAlbumOrderStatus")
    public void changeAlbumOrderStatus() {
    }


}
