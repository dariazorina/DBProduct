package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_scpaper")
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
    private String type;
    private String title;
    private String title_rus;

    private String author;          // TODO: исходные данные по авторам - массив строк)) Нужно удалить потом
    @ManyToMany
    @JoinTable(
            name = "scpaper_author",
            joinColumns = @JoinColumn(name = "scpaper_id", referencedColumnName = "scpaper_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "person_id"))
    private List<Person> authorList;

    private String masthead;

    @Column(name = "abstract")
    private String abstractField;

    private String url;  // TODO: исходные данные по авторам - массив строк)) Нужно удалить потом
    @ManyToMany
    @JoinTable(
            name = "scpaper_link",
            joinColumns = @JoinColumn(name = "scpaper_id", referencedColumnName = "scpaper_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;

    private String misc;

    private String hashtags;  // TODO: исходные данные по авторам - массив строк)) Нужно удалить потом
    @ManyToMany
    @JoinTable(
            name = "scpaper_hashtag",
            joinColumns = @JoinColumn(name = "scpaper_id", referencedColumnName = "scpaper_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "hashtag_id"))
    private List<HashTag> hashtagList;
}
