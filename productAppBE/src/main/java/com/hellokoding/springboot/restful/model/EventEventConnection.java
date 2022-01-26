package com.hellokoding.springboot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="t_connect_event_event")
@Data
public class EventEventConnection {

    @Id
    @Column(name = "connection_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @JsonIgnore
    @ToString.Exclude
    private Event event;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "connected_event_id")
    @JsonIgnore
    @ToString.Exclude
    private Event connectedEvent;

    private String connection;
    private String comment;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof EventEventConnection))
            return false;

        return
                id != null &&
                        id.equals(((EventEventConnection) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
