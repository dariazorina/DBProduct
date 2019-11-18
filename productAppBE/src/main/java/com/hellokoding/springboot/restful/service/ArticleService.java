package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.ArticleRepository;
import com.hellokoding.springboot.restful.model.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> findAll() {
        List<Article> all = articleRepository.findAll();
        return all;
    }

    public Optional<Article> findById(Integer id) {
        Optional<Article> byId = articleRepository.findById(id);
        Article article = byId.get();
        return byId;
    }

    public Article save(Article stock) {
        return articleRepository.save(stock);
    }

    public void deleteById(Integer id) {
        articleRepository.deleteById(id);
    }
}
