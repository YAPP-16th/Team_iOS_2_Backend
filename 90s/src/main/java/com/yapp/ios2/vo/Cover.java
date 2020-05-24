package com.yapp.ios2.vo;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "cover")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cover {

    @Id
    @GeneratedValue
    private Long uid;

    @Column
    private String name;

    @Column
    private String path;
}
