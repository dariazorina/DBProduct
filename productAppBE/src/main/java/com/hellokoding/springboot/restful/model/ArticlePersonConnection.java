package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="t_connect_article_person")
@Data
public class ArticlePersonConnection {

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
    @JoinColumn(name = "person_id")
    @JsonIgnore
    @ToString.Exclude
    private Person person;

    private String connection;
    private String comment;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ArticlePersonConnection))
            return false;

        return
                id != null &&
                        id.equals(((ArticlePersonConnection) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
