package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "t_scpapers")
@Entity
@Data
public class Scpaper {
    @Id
    @Column(name = "scpaper_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer movement_id;
    private Integer country_id;
    private String lang;
    private Integer year;
    private String title;
    private String title_rus;
    private String masthead;
    private String abstract_;
    private String misc;

    private String author;          // TODO: исходные данные по авторам - массив строк)) Нужно удалить потом
    @ManyToMany
    @JoinTable(
            name = "scpaper_author",
            joinColumns = @JoinColumn(name = "scpaper_id", referencedColumnName = "scpaper_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "person_id"))
    private List<Person> authorList;

}
