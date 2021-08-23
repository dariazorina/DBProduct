package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_status")
@Data

public class Status {
    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
