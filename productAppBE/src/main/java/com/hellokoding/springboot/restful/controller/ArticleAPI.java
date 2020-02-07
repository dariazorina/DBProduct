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

    @GetMapping("/search")
    public ResponseEntity<List<Article>> search(@RequestParam(name = "t", required = false) String q) {
        List<Article> search = articleService.search(q);
        return ResponseEntity.ok(search);
    }

    @GetMapping
    public ResponseEntity<List<Article>> findAll() {
        List<Article> all = articleService.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Article article) {
        return ResponseEntity.ok(articleService.save(article));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> findById(@PathVariable Integer id) {
        Optional<Article> stock = articleService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> update(@PathVariable Integer id, @Valid @RequestBody Article article) {
        article.setId(id);
        if (!articleService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(articleService.save(article));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (!articleService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        articleService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
