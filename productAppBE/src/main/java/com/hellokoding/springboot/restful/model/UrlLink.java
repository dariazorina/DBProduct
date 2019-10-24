package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;

@Table(name="t_link")
@Entity
@Data
public class UrlLink {

    @Id
    @Column(name = "link_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
}
