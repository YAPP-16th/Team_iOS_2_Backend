package com.yapp.ios2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SmsDto {

    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class SmsRequestDto {
        private String phoneNumber;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class SmsResponseDto {
        private String num;
    }
}
