package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.UrlLink;
import com.hellokoding.springboot.restful.service.UrlLinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/link")
@Slf4j
@RequiredArgsConstructor
public class UrlLinkAPI {

    private final UrlLinkService urlLinkService;

    @GetMapping
    public ResponseEntity<List<UrlLink>> findAll() {
        return ResponseEntity.ok(urlLinkService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody UrlLink article) {
        return ResponseEntity.ok(urlLinkService.save(article));
    }
}
