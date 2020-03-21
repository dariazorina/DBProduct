package com.hellokoding.springboot.restful.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "person_hashtag")
@Data
public class PersonHashtag {

    @Id
    @Column(name = "person_hashtag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hashtag_id")
    private HashTag hashtag;

    private Integer level;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_hashtag_id")
    private HashTag assigned_hashtag_id;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof PersonHashtag))
            return false;

        return
                id != null &&
                        id.equals(((PersonHashtag) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
