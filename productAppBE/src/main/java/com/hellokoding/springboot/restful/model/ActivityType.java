package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;

@Table(name="t_activity_type")
@Entity
@Data

public class ActivityType {
    @Id
    @Column(name = "activity_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String miscellany;
}
