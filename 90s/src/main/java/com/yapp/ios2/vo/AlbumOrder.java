package com.yapp.ios2.vo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Entity
@Table(name = "albumOrder")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlbumOrder {

    @Id
    @GeneratedValue
    private Long uid;

    @Column
    private String orderCode;

    @Column
    private Integer amount;

    @ManyToOne
    @JoinColumn(name ="AlbumOrderPaperType1_uid")
    private AlbumOrderPaperType1 paperType1;
    @ManyToOne
    @JoinColumn(name ="AlbumOrderPaperType2_uid")
    private AlbumOrderPaperType2 paperType2;
    @ManyToOne
    @JoinColumn(name ="AlbumOrderPostType_uid")
    private AlbumOrderPostType postType;
    @Column
    private String cost;
    @Column
    private String recipient;
    @Column
    private String postalCode;
    @Column
    private String trackingNum;
    @Column
    private String address;
    @Column
    private String addressDetail;
    @Column
    private String phoneNum;
    @Column
    private String message;

    @OneToOne
    @JoinColumn(name ="album_uid")
    private Album album;

    @ManyToOne
    @JoinColumn(name ="user_uid")
    private User user;

    @ManyToOne
    @JoinColumn(name ="albumOrderStatus_uid")
    private AlbumOrderStatus status;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;


    public void setOrderCode(Long albumUid){
        this.orderCode = String.format("%04d%04d%d%d%d%s", this.uid, albumUid, this.paperType1, this.paperType2, this.postalCode, this.created_at.format(DateTimeFormatter.ofPattern("yyMMdd")));
    }
}

//create table order (uid bigint not null, address varchar(255), address_detail varchar(255), cost varchar(255), created_at datetime, message varchar(255), order_code varchar(255), phone_num varchar(255), postal_code varchar(255), recipient varchar(255), tracking_num varchar(255), updated_at datetime, order_paper_type_uid bigint, order_post_type_uid bigint, order_status_uid bigint, primary key (uid)) engine=MyISAM
