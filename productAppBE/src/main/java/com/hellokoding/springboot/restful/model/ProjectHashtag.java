package com.hellokoding.springboot.restful.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "project_hashtag")
@Data
public class ProjectHashtag {

    @Id
    @Column(name = "project_hashtag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

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

        if (!(o instanceof ProjectHashtag))
            return false;

        return
                id != null &&
                        id.equals(((ProjectHashtag) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
