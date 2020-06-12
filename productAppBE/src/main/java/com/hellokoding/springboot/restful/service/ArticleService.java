package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.model.dto.ArticleDto;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<ArticleDto> search(String title, String hash, String author, String lang, String descr, List<Integer> status, String startDate, String endDate) throws ParseException;

    List<ArticleDto> findAll();

    Optional<ArticleDto> findById(Integer id);

    Article save(ArticleDto stock);

    void deleteById(Integer id);
}
