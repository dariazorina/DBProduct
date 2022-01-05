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
//public class Project implements Cloneable, Comparable<Project> {
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
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;

    @ManyToMany
    @JoinTable(
            name = "project_tag",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
    private List<Tag> tagList;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
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


//    public Project() {
//    }
//
//    public Project(List<Movement> movementList, List<UrlLink> linkList, List<Tag> tagList, Status status, List<ProjectArticleConnection> articleConnections, List<ProjectPersonConnection> personConnections, List<ProjectLocationConnection> locationConnections, List<ProjectOrgConnection> orgConnections, List<ProjectEventConnection> eventConnections, List<ProjectProjectConnection> projectConnections, List<ProjectHashtag> hashtagList, Integer foundedYear, Integer closedYear, String rgbSelection, String mainTitle, String otherTitle, String description, String miscellany, Date creationDate, Date changeDate, Date userId) {
//        this.movementList = movementList;
//        this.linkList = linkList;
//        this.tagList = tagList;
//        this.status = status;
//        this.articleConnections = articleConnections;
//        this.personConnections = personConnections;
//        this.locationConnections = locationConnections;
//        this.orgConnections = orgConnections;
//        this.eventConnections = eventConnections;
//        this.projectConnections = projectConnections;
//        this.hashtagList = hashtagList;
//        this.foundedYear = foundedYear;
//        this.closedYear = closedYear;
//        this.rgbSelection = rgbSelection;
//        this.mainTitle = mainTitle;
//        this.otherTitle = otherTitle;
//        this.description = description;
//        this.miscellany = miscellany;
//        this.creationDate = creationDate;
//        this.changeDate = changeDate;
//        this.userId = userId;
//    }
//
//    public Project(Project originalProject) {
//        this.movementList = originalProject.getMovementList();
//        this.linkList = originalProject.getLinkList();
//        this.tagList = originalProject.getTagList();
//        this.status = originalProject.getStatus();
//        this.articleConnections = originalProject.getArticleConnections();
//        this.personConnections = originalProject.getPersonConnections();
//        this.locationConnections = originalProject.getLocationConnections();
//        this.orgConnections = originalProject.getOrgConnections();
//        this.eventConnections = originalProject.getEventConnections();
//        this.projectConnections = originalProject.getProjectConnections();
//        this.hashtagList = originalProject.getHashtagList();
//        this.foundedYear = originalProject.getFoundedYear();
//        this.closedYear = originalProject.getClosedYear();
//        this.rgbSelection = originalProject.getRgbSelection();
//        this.mainTitle = originalProject.getMainTitle();
//        this.otherTitle = originalProject.getOtherTitle();
//        this.description = originalProject.getDescription();
//        this.miscellany = originalProject.getMiscellany();
//        this.creationDate = originalProject.getCreationDate();
//        this.changeDate = originalProject.getChangeDate();
//        this.userId = originalProject.getUserId();
//    }

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

//    @Override
//    public Project clone() {
//        return new Project(this);
//    }
}
