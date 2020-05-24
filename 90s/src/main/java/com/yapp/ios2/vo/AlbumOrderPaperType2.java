package com.yapp.ios2.vo;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "AlbumOrderPaperType2")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlbumOrderPaperType2 {
    @Id
    private Long uid;

    @Column
    private String type;
//    ["gloss", "matt"]

}
