package com.hellokoding.springboot.restful.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_article")
@Data
@ToString
public class Article {

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movement_id", nullable = false)
    private Movement movement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    private Date date;
    private String title;
    private Integer status;


    @Column(name = "title_rus")
    private String titleRus;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "art_author",
            joinColumns = @JoinColumn(name = "art_id", referencedColumnName = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "person_id"))
    private List<Person> authorList;

    private String description;
    private String url;

//    @ManyToMany
//    @JoinTable(
//            name = "art_hashtag",
//            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "article_id"),
//            inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "hashtag_id"))

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    //@ToString.Exclude
    private List<ArticleHashtag> hashtagList;

    private String miscellany;
}
