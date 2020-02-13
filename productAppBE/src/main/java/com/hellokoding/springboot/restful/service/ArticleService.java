package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> search(String title, String hash, String author, Integer status);

    List<Article> findAll();

    Optional<Article> findById(Integer id);

    Article save(Article stock);

    void deleteById(Integer id);
}
