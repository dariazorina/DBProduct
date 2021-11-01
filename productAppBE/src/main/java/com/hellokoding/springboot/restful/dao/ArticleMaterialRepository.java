package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.ArticleMaterialConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleMaterialRepository extends JpaRepository <ArticleMaterialConnection, Integer> {
    @Query("select distinct p " +
            "from ArticleMaterialConnection p " +
            "left join p.material amM " +
            "where (amM.id = :id)")
    List<ArticleMaterialConnection> findByIdSymm(Integer id);

    @Query("select distinct p " +
            "from ArticleMaterialConnection p " +
            "left join p.article amM " +
            "where (amM.id = :id)")
    List<ArticleMaterialConnection> findByIdSimple(Integer id);

}
