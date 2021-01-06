package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="t_connect_article_material")
@Data
public class ArticleMaterialConnection {

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
    @JoinColumn(name = "material_id")
    @JsonIgnore
    @ToString.Exclude
    private Article material;

    @Column(name = "connection_type_id")
    private Integer connection;

    private String comment;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ArticleMaterialConnection))
            return false;

        return
                id != null &&
                        id.equals(((ArticleMaterialConnection) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
