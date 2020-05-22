package com.yapp.ios2.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private Boolean sosial;

    private String email;
    private String password;
}
