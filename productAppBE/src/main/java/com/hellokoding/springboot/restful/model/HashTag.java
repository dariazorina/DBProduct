package com.hellokoding.springboot.restful.model;

import lombok.Data;
import javax.persistence.*;

@Table(name="t_hashtag")
@Entity
@Data

public class HashTag {
    @Id
    @Column(name = "hashtag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private Integer parent_id;
    private String miscellany;

}
