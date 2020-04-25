package com.yapp.ios2.dto;

import lombok.*;

@Getter
@Setter
@ToString
public class KakaoProfileDto {

    private Long id;
    private Properties properties;
    private Kakao_account kakao_account;

    public String getEmail(){
        return this.kakao_account.getEmail();
    }


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
    @NoArgsConstructor
    private static class Kakao_account {
        private String email;

    }
}
