package com.hellokoding.springboot.restful.model;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_person")
@Data
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer movement_id;
    private String surname;
    private String name;
    private String patronymic;
    private String surname_rus;
    private String name_rus;
    private String surname_eng;
    private String name_eng;
    private Integer country_id;
    private String settlement;
    private String occupation;

    @ManyToMany
    @JoinTable(
            name = "person_link",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;

    @ManyToMany
    @JoinTable(
            name = "person_org",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "org_id", referencedColumnName = "org_id"))
    private List<Org> orgList;

    private String description;
    @Column(name = "photo")
    private byte[] photo;

    @ManyToMany
    @JoinTable(
            name = "person_annex",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "annex_id", referencedColumnName = "annex_id"))
    private List<Annex> annexList;


    @ManyToMany
    @JoinTable(
            name = "person_hashtag",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "hashtag_id"))
    private List<HashTag> hashtagList;









}
