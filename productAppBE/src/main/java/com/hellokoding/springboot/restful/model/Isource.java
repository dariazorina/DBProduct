package com.hellokoding.springboot.restful.model;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_isource")
@Data
public class Isource {
    @Id
    @Column(name = "isource_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String movement_id; //todo delete
    @ManyToMany
    @JoinTable(
            name = "isource_movement",
            joinColumns = @JoinColumn(name = "isource_id", referencedColumnName = "isource_id"),
            inverseJoinColumns = @JoinColumn(name = "movement_id", referencedColumnName = "movement_id"))
    private List<Movement> movementList;

    private String title;

    @Column(name="title_rus")
    private String titleRus;

    private String type;
    private String subtype;
    private String dl;
    private String url;


    private String url_links;       // TODO: исходные данные по url_links - массив строк)) Нужно удалить потом
    @ManyToMany
    @JoinTable(
            name = "isource_link",
            joinColumns = @JoinColumn(name = "isource_id", referencedColumnName = "isource_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;


    private String lang;       // TODO: исходные данные по url_links - массив строк)) Нужно удалить потом
    @ManyToMany
    @JoinTable(
            name = "isource_language",
            joinColumns = @JoinColumn(name = "isource_id", referencedColumnName = "isource_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id", referencedColumnName = "language_id"))
    private List<Language> langList;

    private Integer country_id;

    @Column(name="year_create")
    private Integer yearCreate;

    @Column(name="year_update")
    private Integer yearUpdate;

    @Column(name="year_del")
    private Integer yearDel;

    private String access;
    private String update;


    private String moderator;  // TODO: исходные данные по авторам - массив строк)) Нужно удалить потом
    @ManyToMany
    @JoinTable(
            name = "isource_moderator",
            joinColumns = @JoinColumn(name = "isource_id", referencedColumnName = "isource_id"),
            inverseJoinColumns = @JoinColumn(name = "moderator_id", referencedColumnName = "person_id"))
    private List<Person> moderatorList;

    private String description;

    private String hashtags;         // TODO: исходные данные по hashtags - массив строк)) Нужно удалить потом
    @ManyToMany
    @JoinTable(
            name = "isource_hashtag",
            joinColumns = @JoinColumn(name = "isource_id", referencedColumnName = "isource_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "hashtag_id"))
    private List<HashTag> hashtagList;


}
