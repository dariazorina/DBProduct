package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_articles")
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
//    private String author;

//    @Column
//    @ElementCollection(targetClass=String.class)
//    private Set<String> author = new HashSet<String>();


    //   @ElementCollection(fetch = FetchType.EAGER)
    // @CollectionTable(name="article_author", joinColumns=@JoinColumn(name="article_id"))
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "t_articles")
    // @Embedded //?

    @ManyToMany
    @JoinTable(
            name = "ART_AUTHOR",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "author_id"))
    private List<Author> authors;

    //private Set<ArticleAuthor> author;// = new HashSet<>();//= null;


//    @Embedded
//    @AttributeOverrides(value = {
//            @AttributeOverride( name = "firstName", column = @Column(name = "contact_first_name")),
//            @AttributeOverride( name = "lastName", column = @Column(name = "contact_last_name")),
//            @AttributeOverride( name = "phone", column = @Column(name = "contact_phone"))
//    })
//    private ContactPerson contactPerson;

    private String description;
    private String url;
    //   private String[] url_links;
    //  private String[] hashtags;
    private String path;

    public Article() {
    }
}
