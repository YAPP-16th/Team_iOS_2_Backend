package com.yapp.ios2.dto;

import com.yapp.ios2.vo.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @Data
    public static class AccountInfo {
        private Long userUid;
        private String email;
        private String phoneNum;
        private String password;
    }

    @Data
    public static class UserInfo{
        private Long uid;
        private String email;
        private String name;
        private String phoneNum;

        public UserInfo(User user){
            this.uid = user.getUid();
            this.email = user.getEmail();
            this.name = user.getName();
            this.phoneNum = user.getPhone();
        }
    }

    @Data
    public static class UserProfile{
        private Integer albumTotalCount;
        private Integer albumPrintingCount;

        private UserInfo userInfo;

        public void setUserInfo(User user){
            this.userInfo = new UserInfo(user);
        }
    }

}
