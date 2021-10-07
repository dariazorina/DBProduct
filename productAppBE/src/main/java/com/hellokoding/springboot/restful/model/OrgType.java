package com.hellokoding.springboot.restful.model;

import lombok.Data;

import javax.persistence.*;

@Table(name="t_org_type")
@Entity
@Data

public class OrgType {
    @Id
    @Column(name = "org_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;

    @Column(name = "parent_id")
    private Integer parentId;

    private String miscellany;
}
