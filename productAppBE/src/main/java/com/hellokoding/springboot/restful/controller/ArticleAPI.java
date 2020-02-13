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

    //api/v1/article/search?title=title&hash=hash
    @GetMapping("/search")
    public ResponseEntity<List<Article>> search(@RequestParam(name = "title", required = false) String title,
                                                @RequestParam(name = "hash", required = false) String hash,
                                                @RequestParam(name = "author", required = false) String author,
                                                @RequestParam(name = "status", required = false) Integer status) {

            List<Article> search = articleService.search(title, hash, author, status);
            return ResponseEntity.ok(search);
        }

        @GetMapping
        public ResponseEntity<List<Article>> findAll () {
            List<Article> all = articleService.findAll();
            return ResponseEntity.ok(all);
        }

        @PostMapping
        public ResponseEntity create (@Valid @RequestBody Article article){
            return ResponseEntity.ok(articleService.save(article));
        }

        @GetMapping("/{id}")
        public ResponseEntity<Article> findById (@PathVariable Integer id){
            Optional<Article> stock = articleService.findById(id);
            if (!stock.isPresent()) {
                log.error("Id " + id + " is not existed");
                ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(stock.get());
        }

        @PutMapping("/{id}")
        public ResponseEntity<Article> update (@PathVariable Integer id, @Valid @RequestBody Article article){
            article.setId(id);
            if (!articleService.findById(id).isPresent()) {
                log.error("Id " + id + " is not existed");
                ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(articleService.save(article));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity delete (@PathVariable Integer id){
            if (!articleService.findById(id).isPresent()) {
                log.error("Id " + id + " is not existed");
                ResponseEntity.badRequest().build();
            }
            articleService.deleteById(id);
            return ResponseEntity.ok().build();
        }
    }
