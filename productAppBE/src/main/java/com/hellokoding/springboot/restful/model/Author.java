package com.hellokoding.springboot.restful.model;
import lombok.Data;
import javax.persistence.*;


//TODO TO DELETE?
@Table(name="t_author")
@Entity
@Data
public class Author {
    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
