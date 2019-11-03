package com.hellokoding.springboot.restful.model;
import lombok.Data;
import javax.persistence.*;

@Table(name="t_actor")
@Entity
@Data

/////todo to delete?
public class Actor {
    @Id
    @Column(name = "actor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}