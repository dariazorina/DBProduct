package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/article")
@Slf4j
@RequiredArgsConstructor

public class ArticleAPI {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<List<Article>> findAll() {
        return ResponseEntity.ok(articleService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Article article) {
        return ResponseEntity.ok(articleService.save(article));
    }
//utils
    @GetMapping("/fillAuthorTable")
    public ResponseEntity fillAuthorTable() {
        // localhost:8098/api/v1/article/fillAuthorTable   {get}  --
        articleService.fillAuthorTable();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenAuthorAndArticle")
    public ResponseEntity initializeReferenceBetweenAuthorAndArticle() {
        //  localhost:8098/api/v1/article/initializeReferenceBetweenAuthorAndArticle
        articleService.initializeReferenceBetweenAuthorAndArticle();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fillHashTagTable")
    public ResponseEntity fillHashTagTable() {
        // localhost:8098/api/v1/article/fillHashTagTable   {get}  --
        articleService.fillHashTagTable();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenHashTagAndArticle")
    public ResponseEntity initializeReferenceBetweenHashTagAndArticle() {
        //  localhost:8098/api/v1/article/initializeReferenceBetweenHashTagAndArticle
        articleService.initializeReferenceBetweenHashTagAndArticle();
        return ResponseEntity.ok().build();
    }
///// utils end

    @GetMapping("/{id}")
    public ResponseEntity<Article> findById(@PathVariable Long id) {
        Optional<Article> stock = articleService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @Valid @RequestBody Article article) {
        if (!articleService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(articleService.save(article));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (!articleService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        articleService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
