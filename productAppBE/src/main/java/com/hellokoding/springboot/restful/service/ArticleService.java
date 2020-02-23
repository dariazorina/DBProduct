package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Article;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> search(String title, String hash, String author, String lang, String descr, List<Integer> status, String startDate, String endDate) throws ParseException;

    List<Article> findAll();

    Optional<Article> findById(Integer id);

    Article save(Article stock);

    void deleteById(Integer id);
}
