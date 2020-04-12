package com.yapp.ios2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumOwnerDto {
    private Long albumUid;
    private Long userUid;
    private String role;
}
