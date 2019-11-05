package com.hellokoding.springboot.restful.model;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name="t_country")
@Data

public class Country {
    @Id
    @Column(name = "country_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;

}

