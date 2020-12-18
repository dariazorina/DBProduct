package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_type_id", nullable = false)
    private MaterialType mtype;

    private Date date;
    private String title;
    private Integer status;


    @Column(name = "title_rus")
    private String titleRus;

    private String description;
    private String text;

    @ManyToMany
    @JoinTable(
            name = "art_link",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    //@ToString.Exclude
    private List<ArticleHashtag> hashtagList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ArticlePersonConnection> personConnections;

    @OneToMany(mappedBy = "article", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<ArticleOrgConnection> orgConnections;

    @OneToMany(mappedBy = "article", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<ArticleMaterialConnection> materialConnections;

    @OneToMany(mappedBy = "article", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<ArticleLocationConnection> locationConnections;

    private String miscellany;
}
