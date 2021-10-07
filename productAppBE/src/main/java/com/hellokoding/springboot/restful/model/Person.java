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
public class Person implements Comparable<Person> {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "person_movement",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "movement_id", referencedColumnName = "movement_id"))
    private List<Movement> movementList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "death_year")
    private Integer deathYear;

//    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    @ToString.Exclude
//    private List<SurnameNamePatr> snpList;


    @ManyToMany
    @JoinTable(
            name = "person_snp",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "snp_id", referencedColumnName = "snp_id"))
    private List<SurnameNamePatr> snpList;


    @ManyToMany
    @JoinTable(
            name = "person_activity_type",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_type_id", referencedColumnName = "activity_type_id"))
    private List<ActivityType> activityTypeList;

//    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    @ToString.Exclude //    @JsonIgnore
//    private List<Position> occupation;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<OrgPersonConnection> orgConnections;


    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude //    @JsonIgnore
    private List<ArticlePersonConnection> articleConnections;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    //todo? JsonManagedReference and JsonBackReference https://stackoverflow.com/questions/3325387/infinite-recursion-with-jackson-json-and-hibernate-jpa-issue
    private List<PersonHashtag> hashtagList;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    private List<PersonLocationConnection> locationConnections;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<PersonPersonConnection> personConnections;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<PersonIsourceConnection> isourceConnections;

//    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    @ToString.Exclude
//    @JsonIgnore
//    private List<PersonEventConnection> eventConnections;


    @ManyToMany
    @JoinTable(
            name = "person_link",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "link_id"))
    private List<UrlLink> linkList;


    private String description;
    private String miscellany;

    @Column(name = "rgb_selection")
    private String rgbSelection;

    //    columnDefinition="BLOB"
//    @Lob  //was an error bytea - bigint

//    working version, photo should be saved in FS now
//    @Column(name = "photo")   //, columnDefinition="mediumblob")
//    @Basic(fetch = FetchType.LAZY)
//    private byte[] photo;


    @Override
    public boolean equals(Object obj) {
        return ((Person) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(Person obj) {

        String name = "", name1 = "";

        for (int i = 0; i < snpList.size(); i++)
            if (snpList.get(i).getPriority() == 1) {
                name = snpList.get(i).getSurname();
            }

        for (int i = 0; i < obj.snpList.size(); i++)
            if (obj.snpList.get(i).getPriority() == 1) {
                name1 = obj.snpList.get(i).getSurname();
            }

        return name.compareToIgnoreCase(name1);
    }
}
