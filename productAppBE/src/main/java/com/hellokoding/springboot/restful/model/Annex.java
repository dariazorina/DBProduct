package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;

@Table(name="t_annex")
@Entity
@Data
public class Annex {
    @Id
    @Column(name = "annex_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public Annex() {
    }
}