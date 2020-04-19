package com.yapp.ios2.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoProfileDto {

    private Long id;
    private Properties properties;
    private Kakao_account kakao_account;

    @Getter
    @Setter
    @ToString
    private static class Properties {
        private String nickname;
        private String thumbnail_image;
        private String profile_image;
    }

    @Getter
    @Setter
    @ToString
    private static class Kakao_account {
        private String email;
    }
}
