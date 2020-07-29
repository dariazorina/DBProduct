package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "movement_id", nullable = false)
//    private Movement movement;

    private String surname;
    private String name;
    private String patronymic;

    @Column(name = "surname_rus")
    private String surnameRus;

    @Column(name = "name_rus")
    private String nameRus;

    @Column(name = "surname_eng")
    private String surnameEng;

    @Column(name = "name_eng")
    private String nameEng;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "death_year")
    private Integer deathYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")//, insertable = false, updatable = false)
//    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    private String settlement;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
//    @JsonIgnore
    private List<Position> occupation;

    @ManyToMany
    @JoinTable(
            name = "person_link",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;

    private String description;
//    @Column(name = "photo")
//    private byte[] photo;

//    @ManyToMany
//    @JoinTable(
//            name = "person_annex",
//            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
//            inverseJoinColumns = @JoinColumn(name = "annex_id", referencedColumnName = "annex_id"))
//    private List<Annex> annexList;


//    @ManyToMany
//    @JoinTable(
//            name = "person_hashtag",
//            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
//            inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "hashtag_id"))


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<PersonHashtag> hashtagList;

    private String miscellany;
}
