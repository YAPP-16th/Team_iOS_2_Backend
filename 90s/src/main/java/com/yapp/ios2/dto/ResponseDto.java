package com.yapp.ios2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ResponseDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JwtDto{
        private String jwt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BooleanDto{
        private Boolean result;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UrlDto{
        private String url;
    }

}
