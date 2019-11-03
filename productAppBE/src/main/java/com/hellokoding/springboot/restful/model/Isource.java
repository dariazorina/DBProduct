package com.hellokoding.springboot.restful.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "t_isources")
@Data
public class Isource {
    @Id
    @Column(name = "isources_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer movement_id;
    private String title;
    private String title_rus;
    private String type;
    private String subtype;
    private String dl;
    private String url;
    ///links, lang
    private Integer country_id;
    private Integer year_create;
    private Integer year_update;
    private Integer year_del;
    private String access;
    private String update;
    //moder
    private String description;
    //hashtags


}
