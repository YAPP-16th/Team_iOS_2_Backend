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
@Table(name = "user")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue
    private Long uid;

    @Column(length = 45, nullable = false)
    private String id;

    @Column(length = 45, nullable = false)
    private String password;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45, nullable = false)
    private String phoneNum;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

}

//    CREATE TABLE IF NOT EXISTS mydb.user (
//        uid INT NOT NULL,
//        id VARCHAR(45) NULL,
//        name VARCHAR(45) NULL,
//        phoneNum VARCHAR(45) NULL,
//        created_at DATETIME NULL,
//        updated_at DATETIME NULL,
//        password VARCHAR(45) NULL,
//        PRIMARY KEY (uid),
//        UNIQUE INDEX usr_srn_UNIQUE (uid ASC) VISIBLE)
//        ENGINE = InnoDB;
