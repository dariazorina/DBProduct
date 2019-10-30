package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;

@Table(name="t_org")
@Entity
@Data
public class Org {
    @Id
    @Column(name = "org_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public Org() {
    }
}