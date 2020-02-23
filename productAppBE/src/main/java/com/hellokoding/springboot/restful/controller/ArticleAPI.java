package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
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
                                                @RequestParam(name = "language", required = false) String lang,
                                                @RequestParam(name = "description", required = false) String descr,
                                                @RequestParam(name = "status", required = false) List<Integer> status,
                                                @RequestParam(name = "startDate", required = false) String startDate,
                                                @RequestParam(name = "endDate", required = false) String endDate) throws ParseException {

        List<Article> search = articleService.search(title, hash, author, lang, descr, status, startDate, endDate);
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
