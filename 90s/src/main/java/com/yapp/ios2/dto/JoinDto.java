package com.yapp.ios2.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinDto {
    private String email;
    private String password;
    private String name;
    private String phone;

    private Boolean sosial;

}
