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


    //////////////////////////////////utils/////////////////////////////////////
    @GetMapping("/fillLinkTableFromArticle")
    public ResponseEntity fillLinkTableFromArticle() {
        // localhost:8098/api/v1/link/fillLinkTableFromArticle
        urlLinkService.fillLinkTableFromArticle();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenLinkAndArticle")
    public ResponseEntity initializeReferenceBetweenLinkAndArticle() {
        //  localhost:8098/api/v1/link/initializeReferenceBetweenLinkAndArticle
        urlLinkService.initializeReferenceBetweenLinkAndArticle();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fillLinkTableFromEvent")
    public ResponseEntity fillLinkTableFromEvent() {
        // localhost:8098/api/v1/link/fillLinkTableFromEvent
        urlLinkService.fillLinkTableFromEvent();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenLinkAndEvent")
    public ResponseEntity initializeReferenceBetweenLinkAndEvent() {
        //  localhost:8098/api/v1/link/initializeReferenceBetweenLinkAndEvent
        urlLinkService.initializeReferenceBetweenLinkAndEvent();
        return ResponseEntity.ok().build();
    }


    @GetMapping("/fillLinkTableFromScpaper")
    public ResponseEntity fillLinkTableFromScpaper() {
        // localhost:8098/api/v1/link/fillLinkTableFromScpaper
        urlLinkService.fillLinkTableFromScpaper();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenLinkAndScpaper")
    public ResponseEntity initializeReferenceBetweenLinkAndScpaper() {
        //  localhost:8098/api/v1/link/initializeReferenceBetweenLinkAndScpaper
        urlLinkService.initializeReferenceBetweenLinkAndScpaper();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fillLinkTableFromIsource")
    public ResponseEntity fillLinkTableFromIsource() {
        // localhost:8098/api/v1/link/fillLinkTableFromIsource
        urlLinkService.fillLinkTableFromIsource();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenLinkAndIsource")
    public ResponseEntity initializeReferenceBetweenLinkAndIsource() {
        //  localhost:8098/api/v1/link/initializeReferenceBetweenLinkAndIsource
        urlLinkService.initializeReferenceBetweenLinkAndIsource();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fillLinkTableFromOrg")
    public ResponseEntity fillLinkTableFromOrg() {
        // localhost:8098/api/v1/link/fillLinkTableFromOrg
        urlLinkService.fillLinkTableFromOrg();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenLinkAndOrg")
    public ResponseEntity initializeReferenceBetweenLinkAndOrg() {
        //  localhost:8098/api/v1/link/initializeReferenceBetweenLinkAndOrg
        urlLinkService.initializeReferenceBetweenLinkAndOrg();
        return ResponseEntity.ok().build();
    }
///// utils end
}
