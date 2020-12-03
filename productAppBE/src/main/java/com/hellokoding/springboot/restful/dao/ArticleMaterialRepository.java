package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.ArticleMaterialConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleMaterialRepository extends JpaRepository <ArticleMaterialConnection, Integer> {
}
