package com.yapp.ios2.vo;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "noticeAgreement")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NoticeAgreement {

    @Id
    @GeneratedValue
    private Long uid;

    @Column(columnDefinition = "boolean default false")
    private Boolean eventNotice;

    @Column(columnDefinition = "boolean default false")
    private Boolean albumEndNotice;

    @Column(columnDefinition = "boolean default false")
    private Boolean invitationNotice;

    @Column(columnDefinition = "boolean default false")
    private Boolean orderNotice;

    @OneToOne
    @JoinColumn(name ="UserUid", referencedColumnName="uid")
    private User user;

}
