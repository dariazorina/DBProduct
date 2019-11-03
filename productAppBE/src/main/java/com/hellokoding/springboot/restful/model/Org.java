package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name="t_org")
@Entity
@Data
public class Org {
    @Id
    @Column(name = "org_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer movement_id;
    private Integer country_id;
    private String settlement;
    private String name_rus;
    private String abbr_rus;
    private String name;
    private String abbr;
    private String name_eng;
    private String abbr_eng;
    private String type;
    private Integer founded;
    private Integer closed;
    private String address;
    private String description;

    private String actors;  // TODO: исходные данные по авторам - массив строк)) Нужно удалить потом
    @ManyToMany
    @JoinTable(
            name = "org_person",
            joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "org_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "person_id"))
    private List<Person> actorList;

}