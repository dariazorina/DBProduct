package com.hellokoding.springboot.restful.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "event_hashtag")
@Data
public class EventHashtag {

    @Id
    @Column(name = "event_hashtag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hashtag_id")
    private HashTag hashtag;

    private Integer level;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(name = "assigned_hashtag_id")
    private HashTag assigned_hashtag;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof EventHashtag))
            return false;

        return
                id != null &&
                        id.equals(((EventHashtag) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
