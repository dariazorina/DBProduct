package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "art_material_type")
@Data
public class ArticleMaterialType {

    @Id
    @Column(name = "art_material_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_type_id", nullable = false)
    private MaterialType materialType;

    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_material_type_id", nullable = false)
    private MaterialType assigned_material_type;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ArticleMaterialType))
            return false;

        return  id != null &&
                id.equals(((ArticleMaterialType)o).getId());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }
}
