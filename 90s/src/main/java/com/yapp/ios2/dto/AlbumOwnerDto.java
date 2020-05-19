package com.yapp.ios2.dto;

import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOwner;
import com.yapp.ios2.vo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class AlbumOwnerDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AlbumOwnerInfo{

        private Long albumUid;
        private String name;
        private Long userUid;
        private String role;

    }

    public static List<AlbumOwnerInfo> convertFromAlbumOwnerListToAlbumOwnerInfoList(List<AlbumOwner> albumOwners){
        List<AlbumOwnerInfo> albumOwnerInfos = new ArrayList();

        for(AlbumOwner albumOwner : albumOwners){
            albumOwnerInfos.add(new AlbumOwnerInfo(
                    albumOwner.getAlbum().getUid(),
                    albumOwner.getUser().getName(),
                    albumOwner.getUser().getUid(),
                    albumOwner.getRole()));
        }

        return albumOwnerInfos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AlbumOwnerInfos{
        private List<AlbumOwnerInfo> albumOwnerInfos;
    }

}
