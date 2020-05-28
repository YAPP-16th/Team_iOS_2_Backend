package com.yapp.ios2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.service.AlbumOrderService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.AlbumOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album/order/*")
public class AlbumOrderController {

    @Autowired
    AlbumOrderService albumOrderService;
    @Autowired
    UserService userService;



    @PostMapping("/createAlbumOrder")
    public AlbumOrder createAlbumOrder(@AuthenticationPrincipal UserDetails userDetail, @RequestBody AlbumDto.AlbumOrderInfo albumOrderInfo) throws Exception{
        AlbumOrder newAlbumOrder = albumOrderService.createAlbumOrder(albumOrderInfo, userService.getUserByEmail(userDetail.getUsername()));
        return newAlbumOrder;

    }

    @PostMapping("/changeAlbumOrderStatus")
    public void changeAlbumOrderStatus() {
    }


}
