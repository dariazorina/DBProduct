package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_article")
@Data
@ToString
public class Article implements Comparable<Article> {

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "article_movement",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "movement_id", referencedColumnName = "movement_id"))
    private List<Movement> movementList = new ArrayList<Movement>();



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_type_id", nullable = false)
    private MaterialType mtype;

    private Date date;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;


    @Column(name = "title_rus")
    private String titleRus;

    private String description;
    private String text;

    @Column(name = "rgb_selection")
    private String rgbSelection;

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

    @OneToMany(mappedBy = "article", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<ProjectArticleConnection> projectConnections;

    private String miscellany;

//    public Article() {
//    }

//    public Article(List<Movement> movementList, Language language, MaterialType mtype, Date date, String title, Status status, String titleRus, String description, String text, String rgbSelection, List<UrlLink> linkList, List<ArticleHashtag> hashtagList, List<ArticlePersonConnection> personConnections, List<ArticleOrgConnection> orgConnections, List<ArticleMaterialConnection> materialConnections, List<ArticleLocationConnection> locationConnections, List<ProjectArticleConnection> projectConnections, String miscellany) {
//        this.movementList = movementList;
//        this.language = language;
//        this.mtype = mtype;
//        this.date = date;
//        this.title = title;
//        this.status = status;
//        this.titleRus = titleRus;
//        this.description = description;
//        this.text = text;
//        this.rgbSelection = rgbSelection;
//        this.linkList = linkList;
//        this.hashtagList = hashtagList;
//        this.personConnections = personConnections;
//        this.orgConnections = orgConnections;
//        this.materialConnections = materialConnections;
//        this.locationConnections = locationConnections;
//        this.projectConnections = projectConnections;
//        this.miscellany = miscellany;
//    }
//
//    public Article(Article articleOrig) {
//        this.movementList = new ArrayList<>();
//        this.movementList.addAll(articleOrig.getMovementList());
//
//        this.language = articleOrig.getLanguage();
//        this.mtype = articleOrig.getMtype();
//        this.date = articleOrig.getDate();
//        this.title = articleOrig.getTitle();
//        this.status = articleOrig.getStatus();
//        this.titleRus = articleOrig.getTitleRus();
//        this.description = articleOrig.getDescription();
//        this.text = articleOrig.getText();
//        this.rgbSelection = articleOrig.getRgbSelection();
//
//        this.linkList = new ArrayList<>();
//        this.linkList.addAll(articleOrig.getLinkList());
//
//        this.hashtagList = new ArrayList<>();
//        this.hashtagList.addAll(articleOrig.getHashtagList());
//
//        this.personConnections = new ArrayList<>();
//        this.personConnections.addAll(articleOrig.getPersonConnections());
//
//        this.orgConnections = new ArrayList<>();
//        this.orgConnections.addAll(articleOrig.getOrgConnections());
//
//        this.materialConnections = new ArrayList<>();
//        this.materialConnections.addAll(articleOrig.getMaterialConnections());
//
//        this.locationConnections = new ArrayList<>();
//        this.locationConnections.addAll(articleOrig.getLocationConnections());
//
//        this.projectConnections = new ArrayList<>();
//        this.projectConnections.addAll(articleOrig.getProjectConnections());
//
//        this.miscellany = miscellany;
//    }

    @Override
    public boolean equals(Object obj) {
        return ((Article) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(Article obj) {
        return titleRus.compareToIgnoreCase(obj.getTitleRus());
    }
}
