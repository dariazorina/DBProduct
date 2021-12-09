package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "t_project")
@Entity
@Data
public class Project implements Comparable<Project> {
    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "project_movement",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "movement_id", referencedColumnName = "movement_id"))
    private List<Movement> movementList;

    @ManyToMany
    @JoinTable(
            name = "project_link",
            joinColumns = @JoinColumn(name = "prject_id", referencedColumnName = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;

    @ManyToMany
    @JoinTable(
            name = "project_tag",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
    private List<Tag> tagList;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", nullable = false)
    private Status status;


    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude //    @JsonIgnore
    private List<ProjectArticleConnection> articleConnections;

    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<ProjectPersonConnection> personConnections;

    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<ProjectLocationConnection> locationConnections;

    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<ProjectOrgConnection> orgConnections;

    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<ProjectEventConnection> eventConnections;

    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<ProjectProjectConnection> projectConnections;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<ProjectHashtag> hashtagList;



    @Column(name = "founded_year")
    private Integer foundedYear;
    @Column(name = "closed_year")
    private Integer closedYear;

    @Column(name = "rgb_selection")
    private String rgbSelection;

    @Column(name = "main_title")
    private String mainTitle;
    @Column(name = "other_title")
    private String otherTitle;

    private String description;
    private String miscellany;

    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "change_date")
    private Date changeDate;

    @Column(name = "user_id")
    private Date userId;


    @Override
    public boolean equals(Object obj) {
        return ((Project) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(Project obj) {
        return this.mainTitle.compareToIgnoreCase(obj.mainTitle);
    }
}
