package com.yapp.ios2.dto;

import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.AlbumOrderPaperType;
import com.yapp.ios2.vo.AlbumOrderPostType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AlbumDto {

    @Data
    public static class AlbumUidDto{
        private Long uid;
    }


    @Data
    public static class AlbumInfoDto{
        private Long albumUid;
        private Integer photoLimit;
        private String name;
        private Long layoutUid;
        private LocalDate endDate;

        public void setEndDate(String stringEndDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            this.endDate = LocalDate.parse(stringEndDate, formatter);
        }

        public AlbumInfoDto(Album album){
            this.albumUid = album.getUid();
            this.photoLimit = album.getPhotoLimit();
            this.name = album.getName();
            this.layoutUid = album.getLayoutUid();
            this.endDate = album.getEndDate();
        }

    }

    @Data
    public static class AlbumOrderInfo{
        private Long paperType;
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
