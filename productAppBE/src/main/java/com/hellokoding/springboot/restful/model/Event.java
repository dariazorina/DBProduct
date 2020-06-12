package com.hellokoding.springboot.restful.model;

import lombok.Data;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movement_id", nullable = false)
    private Movement movement;

    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    private String settlement;
    private String location;
    private String type;


    @ManyToMany
    @JoinTable(
            name = "event_org",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "org_id", referencedColumnName = "org_id"))
    private List<Org> orgList;


    @ManyToMany
    @JoinTable(
            name = "event_actor",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "person_id"))
    private List<Person> actorList;

    private String title;
    private String description;


    @ManyToMany
    @JoinTable(
            name = "event_link",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;


    @ManyToMany
    @JoinTable(
            name = "event_hashtag",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "hashtag_id"))
    private List<HashTag> hashtagList;


//    private String annex;  // TODO: исходные данные по авторам - массив строк)) Нужно удалить потом
//    @ManyToMany
//    @JoinTable(
//            name = "event_annex",
//            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "event_id"),
//            inverseJoinColumns = @JoinColumn(name = "annex_id", referencedColumnName = "annex_id"))
//    private List<Annex> annexList;
}
