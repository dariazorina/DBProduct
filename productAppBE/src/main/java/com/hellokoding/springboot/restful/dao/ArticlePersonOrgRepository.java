package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.ArticlePersonConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticlePersonOrgRepository extends JpaRepository <ArticlePersonConnection, Integer> {
}
