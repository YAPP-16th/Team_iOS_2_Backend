package com.yapp.ios2.vo;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "AlbumOrderStatus")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class AlbumOrderStatus {
    @GeneratedValue
    @Id
    private Long uid;

    @Column
    String status;
}
