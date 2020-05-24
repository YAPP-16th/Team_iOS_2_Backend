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

    @ManyToOne
    String status;
//    [입금대기, 배송준비, 배송중, 배송완료]
//    [pending, processing, shipping, done]

    public AlbumOrderStatus(String status) {
        this.status = status;
    }
}
