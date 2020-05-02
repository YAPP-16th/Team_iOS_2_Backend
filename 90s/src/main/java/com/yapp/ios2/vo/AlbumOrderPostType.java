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
public class AlbumOrderPostType {
    @GeneratedValue
    @Id
    private Long uid;

    @Column
    private String type;
}

