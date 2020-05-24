package com.yapp.ios2.vo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "invitation")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Invitation {

    @Id
    @GeneratedValue
    private Long uid;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name ="album_uid", referencedColumnName="uid")
    private Album album;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name ="user_uid")
    private User user;

    @Column
    private String code;

    @CreationTimestamp
    private LocalDateTime created_at;

}
