package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="t_connect_event_org")
@Data
public class OrgEventConnection {

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
    @JoinColumn(name = "event_id")
    @JsonIgnore
    @ToString.Exclude
    private Event event;

    private String connection;
    private String comment;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof OrgEventConnection))
            return false;

        return
                id != null &&
                        id.equals(((OrgEventConnection) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
