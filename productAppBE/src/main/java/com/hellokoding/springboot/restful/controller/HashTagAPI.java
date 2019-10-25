package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.HashTag;
import com.hellokoding.springboot.restful.service.HashTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hashtag")
@Slf4j
@RequiredArgsConstructor

public class HashTagAPI {

    private final HashTagService hashTagService;

    @GetMapping
    public ResponseEntity<List<HashTag>> findAll() {
        return ResponseEntity.ok(hashTagService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody HashTag article) {
        return ResponseEntity.ok(hashTagService.save(article));
    }

    ////utils////
    @GetMapping("/fillHashTagTable")
    public ResponseEntity fillHashTagTable() {
        // localhost:8098/api/v1/article/fillHashTagTable
        hashTagService.fillHashTagTable();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenHashTagAndArticle")
    public ResponseEntity initializeReferenceBetweenHashTagAndArticle() {
        //  localhost:8098/api/v1/article/initializeReferenceBetweenHashTagAndArticle
        hashTagService.initializeReferenceBetweenHashTagAndArticle();
        return ResponseEntity.ok().build();
    }
   /////// ////

}
