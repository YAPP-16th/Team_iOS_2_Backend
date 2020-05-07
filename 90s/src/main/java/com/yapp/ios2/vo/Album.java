package com.yapp.ios2.vo;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "album")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue
    private Long uid;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer photoLimit;

    @Column(name = "layout_uid")
    private Long layoutUid;

    @Column(columnDefinition = "integer default 0")
    private Integer count;

    @Column(columnDefinition = "varchar(45) default 'ready'")
    private String orderStatus;

    @Column
    private LocalDate endDate;

    @Column(columnDefinition = "boolean default false")
    private boolean isComplete;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    public Album() {
    }

    public Album(String name, Integer photoLimit, Long layoutUid) {
        this.name = name;
        this.photoLimit = photoLimit;
        this.layoutUid = layoutUid;
    }
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