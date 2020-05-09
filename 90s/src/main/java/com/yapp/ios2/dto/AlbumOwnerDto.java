package com.yapp.ios2.dto;

import com.yapp.ios2.vo.AlbumOwner;
import com.yapp.ios2.vo.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class AlbumOwnerDto {
    @Data
    public static class AlbumOwnerInfo{
        private UserDto.UserInfo user;
        private String role;


        public AlbumOwnerInfo(User user, String role){
            this.user = new UserDto.UserInfo(user);
            this.role = role;
        }
    }

    public static List<AlbumOwnerInfo> convertFromAlbumOwnerListToAlbumOwnerInfoList(List<AlbumOwner> albumOwners){
        List<AlbumOwnerInfo> albumOwnerInfos = new ArrayList();

        for(AlbumOwner albumOwner : albumOwners){
            albumOwnerInfos.add(new AlbumOwnerInfo(albumOwner.getUser(), albumOwner.getRole()));
        }

        return albumOwnerInfos;
    }

}
