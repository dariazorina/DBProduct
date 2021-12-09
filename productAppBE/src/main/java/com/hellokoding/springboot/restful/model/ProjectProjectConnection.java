package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="t_connect_project_project")
@Data
public class ProjectProjectConnection {

    @Id
    @Column(name = "connection_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @JsonIgnore
    @ToString.Exclude
    private Project project;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "connected_project_id")
    @JsonIgnore
    @ToString.Exclude
    private Project connectedProject;

    private String connection;
    private String comment;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ProjectProjectConnection))
            return false;

        return
                id != null &&
                        id.equals(((ProjectProjectConnection) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
