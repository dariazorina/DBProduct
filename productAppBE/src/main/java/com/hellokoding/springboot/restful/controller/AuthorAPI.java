package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.Author;
import com.hellokoding.springboot.restful.model.Person;
import com.hellokoding.springboot.restful.service.AuthorService;
import com.hellokoding.springboot.restful.service.PersonService;
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

//todo delete class
public class AuthorAPI {
    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @PostMapping
    public ResponseEntity
    create(@Valid @RequestBody Person person) {
        return ResponseEntity.ok(personService.save(person));
    }

    //utils
    @GetMapping("/fillAuthorTableFromArticle")
    public ResponseEntity fillAuthorTableFromArticle() {
        // localhost:8098/api/v1/author/fillAuthorTableFromArticle
        //personService.fillAuthorTableFromArticle();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenAuthorAndArticle")
    public ResponseEntity initializeReferenceBetweenAuthorAndArticle() {
        //  localhost:8098/api/v1/author/initializeReferenceBetweenAuthorAndArticle
        personService.initializeReferenceBetweenAuthorAndArticle();
        return ResponseEntity.ok().build();
    }
    ///////////////////////

}
