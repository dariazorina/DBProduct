package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="t_connect_org_isource")
@Data
public class OrgIsourceConnection {

    @Id
    @Column(name = "connection_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "org_id")
    @JsonIgnore
    @ToString.Exclude
    private Org org;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "isource_id")
    @JsonIgnore
    @ToString.Exclude
    private Isource isource;

    private String connection;
    private String comment;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof OrgIsourceConnection))
            return false;

        return
                id != null &&
                        id.equals(((OrgIsourceConnection) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
