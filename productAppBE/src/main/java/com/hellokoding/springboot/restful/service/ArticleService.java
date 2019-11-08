package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.ArticleRepository;
import com.hellokoding.springboot.restful.dao.HashTagRepository;
import com.hellokoding.springboot.restful.dao.UrlLinkRepository;
import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.model.Author;
import com.hellokoding.springboot.restful.model.HashTag;
import com.hellokoding.springboot.restful.model.UrlLink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> findAll() {
        List<Article> all = articleRepository.findAll();
        return all;
    }

    public Optional<Article> findById(Long id) {
        Optional<Article> byId = articleRepository.findById(Math.toIntExact(id));
        Article article = byId.get();
        return byId;
    }

    public Article save(Article stock) {
        return articleRepository.save(stock);
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(Math.toIntExact(id));
    }
}
