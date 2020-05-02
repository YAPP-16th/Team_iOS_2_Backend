package com.yapp.ios2.dto;

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
    public static class AlbumInfoDto{

        private Integer photoLimit;
        private String name;
        private Long layoutUid;
        private LocalDate endDate;

        public void setEndDate(String stringEndDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            this.endDate = LocalDate.parse(stringEndDate, formatter);
        }

    }

    @Data
    public static class AlbumOwnerDto {
        private Long albumUid;
        private Long userUid;
        private String role;
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
