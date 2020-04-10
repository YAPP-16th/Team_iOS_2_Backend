package com.yapp.ios2.vo;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "photo")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Photo {

    @Id
    @GeneratedValue
    private Long uid;

    @Column(length = 100)
    private String url;

    @Column(name="photoOrder")
    private Integer photoOrder;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Column(name="uploader")
    private Integer uploader;

    @Column(name="album_uid")
    private Integer albumUid;

    public Photo(String url, Integer photoOrder, Integer uploader, Integer albumUid) {
        this.url = url;
        this.photoOrder = photoOrder;
        this.uploader = uploader;
        this.albumUid = albumUid;
    }
}

/*
+------------+--------------+------+-----+---------+-------+
| Field      | Type         | Null | Key | Default | Extra |
+------------+--------------+------+-----+---------+-------+
| uid        | int          | NO   | PRI | NULL    |       |
| url        | varchar(100) | YES  |     | NULL    |       |
| order      | int          | YES  |     | NULL    |       |
| created_at | datetime     | YES  |     | NULL    |       |
| updated_at | datetime     | YES  |     | NULL    |       |
| uploader   | int          | NO   | PRI | NULL    |       |
| album_uid  | int          | NO   | PRI | NULL    |       |
+------------+--------------+------+-----+---------+-------+
*/
