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
@NoArgsConstructor
public class AlbumOrderStatus {

    @GeneratedValue
    @Id
    private Long uid;

    String status;
//    [pending, ready ,processing, shipping, done]

    public AlbumOrderStatus(String status) {
        this.status = status;
    }
}
