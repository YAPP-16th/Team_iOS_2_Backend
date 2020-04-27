package com.yapp.ios2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoDto {
    @Data
    public static class PhotoInfoDto{
        private Long photoUid;
        private Long albumUid;
    }

}
