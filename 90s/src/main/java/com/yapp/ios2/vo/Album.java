package com.yapp.ios2.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "album")
@Getter
@Setter
@ToString
public class Album {

    @Id
    @GeneratedValue
    private Long uid;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer photoLimit;

    @Column(name = "layout_uid")
    private Integer layoutUid;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;
}

/*
+-------------+-------------+------+-----+---------+-------+
| Field       | Type        | Null | Key | Default | Extra |
+-------------+-------------+------+-----+---------+-------+
| uid         | int         | NO   | PRI | NULL    |       |
| name        | varchar(45) | YES  |     | NULL    |       |
| photoLimit  | int         | YES  |     | NULL    |       |
| created_at  | datetime    | YES  |     | NULL    |       |
| updated_at  | datetime    | YES  |     | NULL    |       |
| layout_uid  | int         | NO   | PRI | NULL    |       |
| expiredDate | datetime    | YES  |     | NULL    |       |
+-------------+-------------+------+-----+---------+-------+
*/