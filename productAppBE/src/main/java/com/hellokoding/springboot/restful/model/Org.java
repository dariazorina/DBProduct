package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Table(name="t_org")
@Entity
@Data
public class Org {
    @Id
    @Column(name = "org_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "org_movement",
            joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "org_id"),
            inverseJoinColumns = @JoinColumn(name = "movement_id", referencedColumnName = "movement_id"))
    private List<Movement> movementList;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "location_id", nullable = false)
//    private Location location;

    @OneToMany(mappedBy = "org", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude //    @JsonIgnore
    private List<ArticleOrgConnection> connections;

    private String settlement;

    @Column(name="name_rus")
    private String nameRus;

    @Column(name="abbr_rus")
    private String abbrRus;

    private String name;
    private String abbr;

    @Column(name="name_eng")
    private String nameEng;

    @Column(name="abbr_eng")
    private String abbrEng;

    private String type;
    private Integer founded;
    private Integer closed;

    @OneToMany(mappedBy = "org", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    @ToString.Exclude
//    @JsonIgnore             //was active (without NewPersonDto)
    private List<Position> occupation;


    @ManyToMany
    @JoinTable(
            name = "org_actor",
            joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "org_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "person_id"))
    @JsonIgnore
    @ToString.Exclude
    private List<Person> actorList;

    @ManyToMany
    @JoinTable(
            name = "org_link",
            joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "org_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;

    private String address;
    private String description;
}