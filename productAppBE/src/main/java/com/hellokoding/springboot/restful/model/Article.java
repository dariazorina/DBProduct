package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_article")
@Data
public class Article {

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer movement_id;
    private Integer language_id;
    private Date date;
    private String title;
    private String title_rus;
    private String author;          // TODO: исходные данные по авторам - массив строк)) Нужно удалить потом

    @ManyToMany
    @JoinTable(
            name = "art_author",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "person_id"))
    private List<Person> authorList;

    private String description;
    private String url;
    private String url_links;       // TODO: исходные данные по url_links - массив строк)) Нужно удалить потом

    @ManyToMany
    @JoinTable(
            name = "art_link",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;


    private String hashtags;         // TODO: исходные данные по hashtags - массив строк)) Нужно удалить потом

    @ManyToMany
    @JoinTable(
            name = "art_hashtag",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "hashtag_id"))
    private List<HashTag> hashtagList;

    private String path;

}
