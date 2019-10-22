package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {


}