package com.yapp.ios2.dto;

import lombok.Data;

public class ResponseDto {

    @Data
    public static class JwtDto{
        private String jwt;
    }

    @Data
    public static class BooleanDto{
        private Boolean result;
    }

    @Data
    public static class UrlDto{
        private String url;
    }

}
