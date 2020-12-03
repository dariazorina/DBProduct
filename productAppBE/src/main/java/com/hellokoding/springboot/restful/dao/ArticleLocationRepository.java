package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.ArticleLocationConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLocationRepository extends JpaRepository <ArticleLocationConnection, Integer> {
}
