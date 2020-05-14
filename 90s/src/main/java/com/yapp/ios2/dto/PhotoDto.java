package com.yapp.ios2.dto;

import com.yapp.ios2.vo.AlbumOwner;
import com.yapp.ios2.vo.Photo;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PhotoDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PhotoInfo {
        private Long photoUid;
        private Long albumUid;

        public PhotoInfo(Photo photo){
            this.photoUid = photo.getUid();
            this.albumUid = photo.getAlbum().getUid();
        }
    }

    public static List<PhotoInfo> convertFromPhotoListToPhotoInfoList(List<Photo> photos){
        List<PhotoInfo> photoInfos = new ArrayList();

        for(Photo photo : photos){
            photoInfos.add(new PhotoInfo(photo));
        }

        return photoInfos;
    }

}
