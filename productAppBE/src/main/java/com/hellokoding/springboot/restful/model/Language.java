package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_languages")
@Data

public class Language {
    @Id
    @Column(name = "language_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;
}
