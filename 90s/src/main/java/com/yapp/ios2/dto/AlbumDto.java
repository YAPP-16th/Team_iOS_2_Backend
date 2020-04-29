package com.yapp.ios2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

}
