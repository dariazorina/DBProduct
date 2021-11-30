package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.ArticlePersonConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticlePersonRepository extends JpaRepository <ArticlePersonConnection, Integer> {
}
