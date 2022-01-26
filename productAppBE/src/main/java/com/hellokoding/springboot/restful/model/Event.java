package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_event")
@Data
public class Event {
    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "event_movement",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "movement_id", referencedColumnName = "movement_id"))
    private List<Movement> movementList;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;

    private String type;
    private String title;
    private String description;
    private String miscellany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @Column(name = "rgb_selection")
    private String rgbSelection;



    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<ArticleEventConnection> articleConnections;


    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<PersonEventConnection> personConnections;

    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<LocationEventConnection> locationConnections;

    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<OrgEventConnection> orgConnections;

    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<EventEventConnection> eventConnections;

    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<ProjectEventConnection> projectConnections;
    
    @ManyToMany
    @JoinTable(
            name = "event_link",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<EventHashtag> hashtagList;

    @ManyToMany
    @JoinTable(
            name = "event_tag",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
    private List<Tag> tagList;

    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "change_date")
    private Date changeDate;

    @Column(name = "user_id")
    private Date userId;
}
