package com.hellokoding.springboot.restful.model;
import lombok.Data;
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

    private String movement_id; //todo delete

    @ManyToMany
    @JoinTable(
            name = "org_movement",
            joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "org_id"),
            inverseJoinColumns = @JoinColumn(name = "movement_id", referencedColumnName = "movement_id"))
    private List<Movement> movementList;

    private Integer country_id;
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

    private String actors;  // TODO: исходные данные по авторам - массив строк)) Нужно удалить потом
    @ManyToMany
    @JoinTable(
            name = "org_actor",
            joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "org_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "person_id"))
    private List<Person> actorList;

    private String url;       // TODO: исходные данные по url_links - массив строк)) Нужно удалить потом
    @ManyToMany
    @JoinTable(
            name = "org_link",
            joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "org_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;

    private String address;
    private String description;



}