package com.yapp.ios2.vo;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "AlbumOrderPostType")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlbumOrderPostType {
    @Id
    private Long uid;

    @Column
    private String type;
//    ["normal","fast","faster"]
}

