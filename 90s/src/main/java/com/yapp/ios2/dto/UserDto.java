package com.yapp.ios2.dto;

import com.yapp.ios2.vo.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @Data
    public static class UserInfo{
        private Long userUid;
        private String email;
        private String phoneNum;
        private String password;
    }

    @Data
    public static class UserProfile{
        private Integer albumTotalCount;
        private Integer albumPrintingCount;

        private User user;
    }



}
