package com.yapp.ios2.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AlbumDto {

    private Integer photoLimit;
    private String name;
    private Long layoutUid;

    private LocalDate endDate;

    public void setEndDate(String stringEndDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        this.endDate = LocalDate.parse(stringEndDate, formatter);;
    }

}
