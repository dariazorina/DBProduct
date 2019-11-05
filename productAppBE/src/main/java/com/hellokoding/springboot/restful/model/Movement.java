package com.hellokoding.springboot.restful.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "t_movement")
@Data

public class Movement {
    @Id
    @Column(name="movement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;
}

