package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="t_connect_person_person")
@Data
public class PersonPersonConnection {

    @Id
    @Column(name = "connection_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @JsonIgnore
    @ToString.Exclude
    private Person person;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "connected_person_id")
    @JsonIgnore
    @ToString.Exclude
    private Person connectedPerson;

    private String connection;
    private String comment;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof PersonPersonConnection))
            return false;

        return
                id != null &&
                        id.equals(((PersonPersonConnection) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
