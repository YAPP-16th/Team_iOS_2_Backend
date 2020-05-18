package com.yapp.ios2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinDto {
    private String email;
    private String password;
    private String name;
    private String phone;

    private Boolean sosial;

}
