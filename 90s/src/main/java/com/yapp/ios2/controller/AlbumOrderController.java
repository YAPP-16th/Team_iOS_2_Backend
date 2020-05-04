package com.yapp.ios2.controller;

import com.yapp.ios2.dto.AlbumDto;
import com.yapp.ios2.service.AlbumOrderService;
import com.yapp.ios2.vo.AlbumOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album/order/*")
public class AlbumOrderController {

    @Autowired
    AlbumOrderService albumOrderService;


    @PostMapping("/createAlbumOrder")
    public AlbumOrder createAlbumOrder(AlbumDto.AlbumOrderInfo albumOrderInfo){

        AlbumOrder newAlbumOrder = albumOrderService.createAlbumOrder(albumOrderInfo);
        return newAlbumOrder;

    }

    @PostMapping("/changeAlbumOrderStatus")
    public void changeAlbumOrderStatus() {
    }


}
