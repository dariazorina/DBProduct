package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Table(name = "t_org")
@Entity
@Data
public class Org implements Comparable<Org>{
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", nullable = false)
    private Status status;

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

    @OneToMany(mappedBy = "org", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<ProjectOrgConnection> projectConnections;


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
    @JsonIgnore
    private List<OrgHashtag> hashtagList;

//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "material_type_id", nullable = false)
//    private MaterialType mtype;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", nullable = false)
    private OrgType orgType;

    @Column(name = "founded_year")
    private Integer foundedYear;
    @Column(name = "closed_year")
    private Integer closedYear;

    @Column(name = "rgb_selection")
    private String rgbSelection;

    private String description;
    private String miscellany;

    @Override
    public boolean equals(Object obj) {
        return ((Org) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(Org obj) {
        String name = "", name1 = "";

        for (int i=0 ; i < nameList.size(); i++)
            if (nameList.get(i).getPriority() == 1){
                name = nameList.get(i).getName();
            }

        for (int i = 0 ; i < obj.nameList.size(); i++)
            if (obj.nameList.get(i).getPriority() == 1){
                name1 = obj.nameList.get(i).getName();
            }

        return name.compareToIgnoreCase(name1);
    }
}