package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="t_connect_article_location")
@Data
public class ArticleLocationConnection {

    @Id
    @Column(name = "connection_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @JsonIgnore
    @ToString.Exclude
    private Article article;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @JsonIgnore
    @ToString.Exclude
    private Location location;

    private String connection;
    private String comment;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ArticleLocationConnection))
            return false;

        return
                id != null &&
                        id.equals(((ArticleLocationConnection) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
