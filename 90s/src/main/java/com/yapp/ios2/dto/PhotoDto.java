package com.yapp.ios2.dto;

import com.yapp.ios2.vo.AlbumOwner;
import com.yapp.ios2.vo.Photo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PhotoDto {
    @Data
    public static class PhotoInfoDto{
        private Long photoUid;
        private Long albumUid;

        public PhotoInfoDto(Photo photo){
            this.photoUid = photo.getUid();
            this.albumUid = photo.getAlbum().getUid();
        }
    }

    public static List<PhotoInfoDto> convertFromPhotoListToPhotoInfoList(List<Photo> photos){
        List<PhotoInfoDto> photoInfos = new ArrayList();

        for(Photo photo : photos){
            photoInfos.add(new PhotoInfoDto(photo));
        }

        return photoInfos;
    }

}
