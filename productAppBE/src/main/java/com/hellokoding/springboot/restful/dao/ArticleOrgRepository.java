package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.ArticleOrgConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleOrgRepository extends JpaRepository <ArticleOrgConnection, Integer> {
}
