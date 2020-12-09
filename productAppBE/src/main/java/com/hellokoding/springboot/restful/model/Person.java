package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
//import java.sql.Blob;
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "location_id")//, insertable = false, updatable = false)
////    @JoinColumn(name = "country_id", nullable = false)
//    private Location location;

   // private String settlement;


    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude //    @JsonIgnore
    private List<Position> occupation;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude //    @JsonIgnore
    private List<ArticlePersonConnection> articleConnections;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<PersonHashtag> hashtagList;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<PersonLocationConnection> locationConnections;


    @ManyToMany
    @JoinTable(
            name = "person_link",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;


    private String description;
    private String miscellany;

    //    columnDefinition="BLOB"
//    @Lob  //was an error bytea - bigint
    @Column(name = "photo")//, columnDefinition="mediumblob")
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;
}
