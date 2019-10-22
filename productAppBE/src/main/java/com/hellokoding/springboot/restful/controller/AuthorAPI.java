package com.hellokoding.springboot.restful.controller;


import com.hellokoding.springboot.restful.model.Author;
import com.hellokoding.springboot.restful.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
@Slf4j
@RequiredArgsConstructor
public class AuthorAPI {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> findAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Author article) {
        return ResponseEntity.ok(authorService.save(article));
    }

}
