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
}
