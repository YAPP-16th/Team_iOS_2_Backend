package com.yapp.ios2.vo;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "photo")
@Getter
public class Photo {

    @Id
    @GeneratedValue
    private Long uid;

    @Column(length = 100, nullable = false)
    private String url;


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