package com.hellokoding.springboot.restful.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="t_location")
@Data

public class Location {
    @Id
    @Column(name = "location_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String country;
    private String region;
    private String city;
    private String address;
    private String placement;
    private String miscellany;

    @OneToMany(mappedBy = "location", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude //    @JsonIgnore
    private List<ArticleLocationConnection> articleConnections;

    @OneToMany(mappedBy = "location", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude //    @JsonIgnore
    private List<PersonLocationConnection> personConnections;
}

