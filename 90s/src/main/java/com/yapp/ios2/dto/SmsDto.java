package com.yapp.ios2.dto;

import lombok.*;

@Getter
@Setter
public class SmsDto {

//    @AllArgsConstructor
//    @Builder
//    @Getter
//    @Setter
    @Data
    public static class SmsRequestDto {
        private String phoneNumber;
    }

//    @AllArgsConstructor
//    @Builder
//    @Getter
//    @Setter
    @Data
    public static class SmsResponseDto {
        private String num;
    }
}
