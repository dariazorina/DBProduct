package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;

@Table(name="t_annex")
@Entity
@Data
//File
public class Annex {
    @Id
    @Column(name = "annex_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //private String title; //?
    private String filename;
   // @Column(name = "file")
    //private byte[] file;
}