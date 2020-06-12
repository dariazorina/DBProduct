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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movement_id", nullable = false)
    private Movement movement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    private String lang;
    private Integer year;
    private String type;
    private String title;
    @Column(name = "title_rus")
    private String titleRus;


    @ManyToMany
    @JoinTable(
            name = "scpaper_author",
            joinColumns = @JoinColumn(name = "scpaper_id", referencedColumnName = "scpaper_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "person_id"))
    private List<Person> authorList;

    private String masthead;

    @Column(name = "abstract")
    private String abstractField;


    @ManyToMany
    @JoinTable(
            name = "scpaper_link",
            joinColumns = @JoinColumn(name = "scpaper_id", referencedColumnName = "scpaper_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;

    private String misc;


    @ManyToMany
    @JoinTable(
            name = "scpaper_hashtag",
            joinColumns = @JoinColumn(name = "scpaper_id", referencedColumnName = "scpaper_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "hashtag_id"))
    private List<HashTag> hashtagList;
}
