package com.yapp.ios2.dto;

import com.yapp.ios2.vo.Album;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AlbumDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AlbumUid {
        private Long uid;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AlbumInfo {
        private Long albumUid;
        private Integer photoLimit;
        private String name;
        private Long layoutUid;
        private Long coverUid;
        private LocalDate endDate;

        public void setEndDate(String stringEndDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            this.endDate = LocalDate.parse(stringEndDate, formatter);
        }

        public AlbumInfo(Album album){
            this.albumUid = album.getUid();
            this.photoLimit = album.getPhotoLimit();
            this.name = album.getName();
            this.layoutUid = album.getLayoutUid();
            this.coverUid = album.getCover().getUid();
            this.endDate = album.getEndDate();
        }

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AlbumOrderInfo{
        private Long albumUid;
        private Long paperType1;
        private Long paperType2;
        private Long postType;
        private String cost;
        private String recipient;
        private String postalCode;
        private String trackingNum;
        private String address;
        private String addressDetail;
        private String phoneNum;
        private String message;
    }

}
