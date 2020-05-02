package com.yapp.ios2.vo;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "AlbumOrderPaperType")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class AlbumOrderPaperType {
    @GeneratedValue
    @Id
    private Long uid;

    @Column
    private String type;
}
