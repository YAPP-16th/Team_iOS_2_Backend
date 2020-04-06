package com.yapp.ios2.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class Layout {

    @Id
    @GeneratedValue
    private Long uid;

}
