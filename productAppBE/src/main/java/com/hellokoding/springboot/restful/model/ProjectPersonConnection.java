package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="t_connect_project_person")
@Data
public class ProjectPersonConnection {

    @Id
    @Column(name = "connection_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY) ///EAGER
    @JoinColumn(name = "project_id")
    @JsonIgnore
    @ToString.Exclude
    private Project project;

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

        if (!(o instanceof ProjectPersonConnection))
            return false;

        return
                id != null &&
                        id.equals(((ProjectPersonConnection) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
