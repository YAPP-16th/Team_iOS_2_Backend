package com.yapp.ios2.vo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Entity
@Table(name = "layout")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Layout {

    @Id
    @GeneratedValue
    private Long uid;

//    public Layout() {
//    }
}
