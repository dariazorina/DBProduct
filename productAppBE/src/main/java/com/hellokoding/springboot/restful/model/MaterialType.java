package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;

@Table(name="t_material_type")
@Entity
@Data

public class MaterialType {
    @Id
    @Column(name = "material_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;

    @Column(name = "parent_id")
    private Integer parentId;

    private String miscellany;
}
