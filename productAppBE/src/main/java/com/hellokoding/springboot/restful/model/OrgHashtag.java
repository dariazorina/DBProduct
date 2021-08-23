package com.hellokoding.springboot.restful.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "org_hashtag")
@Data
public class OrgHashtag {

    @Id
    @Column(name = "org_hashtag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_id")
    private Org org;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hashtag_id")
    private HashTag hashtag;

    private Integer level;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(name = "assigned_hashtag")
    private HashTag assigned_hashtag;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof OrgHashtag))
            return false;

        return
                id != null &&
                        id.equals(((OrgHashtag) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
