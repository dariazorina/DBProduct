package com.hellokoding.springboot.restful.model;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Table(name="t_hashtag")
@Entity
@Data

public class HashTag {
    @Id
    @Column(name = "hashtag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;

    @Column(name = "parent_id")
    private Integer parentId;

    private String miscellany;

//    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    private List<ArticleHashtag> hashtagList;

}
