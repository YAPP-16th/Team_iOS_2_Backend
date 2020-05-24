package com.yapp.ios2.vo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "albumOwner")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlbumOwner {
    @Id
    @GeneratedValue
    private Long uid;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name ="album_uid")
    private Album album;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name ="user_uid")
    private User user;

    @Column
    private String role;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

//    public AlbumOwner() {
//    }

//    public AlbumOwner(Long userUid, String role) {
//        this.userUid = userUid;
//        this.role = role;
//    }
}
