package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Table(name = "t_org")
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
    private List<ArticleOrgConnection> articleConnections;  //todo to research


    @OneToMany(mappedBy = "org", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<OrgPersonConnection> personConnections;

    @OneToMany(mappedBy = "org", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<OrgLocationConnection> locationConnections;

    @OneToMany(mappedBy = "org", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<OrgOrgConnection> orgConnections;

    @OneToMany(mappedBy = "org", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<OrgIsourceConnection> isourceConnections;

//    @OneToMany(mappedBy = "org", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    @ToString.Exclude
//    private List<OrgName> nameList;

    @ManyToMany
    @JoinTable(
            name = "org_name",
            joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "org_id"),
            inverseJoinColumns = @JoinColumn(name = "org_name_id", referencedColumnName = "org_name_id"))
    private List<OrgName> nameList;

//    @OneToMany(mappedBy = "org", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    private List<Position> occupation;  //todo to delete?


    @ManyToMany
    @JoinTable(
            name = "org_link",
            joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "org_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "org", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<OrgHashtag> hashtagList;


    private String type;
    //    @Column(name = "founded_year")
    private Integer foundedYear;
    private Integer closedYear;
    private String description;
    private String miscellany;
}