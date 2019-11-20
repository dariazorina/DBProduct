package com.hellokoding.springboot.restful.controller;


import com.hellokoding.springboot.restful.model.HashTag;
import com.hellokoding.springboot.restful.model.Person;
import com.hellokoding.springboot.restful.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
@Slf4j
@RequiredArgsConstructor
public class PersonAPI {
    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<Person>> findAll(@RequestParam(name = "q", required = false) String q) {
        if (!StringUtils.isEmpty(q)){
            List<Person> search = personService.search(q);
            return ResponseEntity.ok(search);

        } else {
            return ResponseEntity.ok(personService.findAll());

        }

    }


    @PostMapping
    public ResponseEntity
    create(@Valid @RequestBody Person person) {
        return ResponseEntity.ok(personService.save(person));
    }

    //////////////////////utils/////////////////////////////////////////
/*    @GetMapping("/fillPersonTableFromArticle")
    public ResponseEntity fillPersonTableFromArticle() {
        // localhost:8098/api/v1/person/fillPersonTableFromArticle
        personService.fillPersonTableFromArticle();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenAuthorAndArticle")
    public ResponseEntity initializeReferenceBetweenAuthorAndArticle() {
        //  localhost:8098/api/v1/person/initializeReferenceBetweenAuthorAndArticle
        personService.initializeReferenceBetweenAuthorAndArticle();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fillPersonTableFromEvent")
    public ResponseEntity fillPersonTableFromEvent() {
        // localhost:8098/api/v1/person/fillPersonTableFromEvent
        personService.fillPersonTableFromEvent();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenActorAndEvent")
    public ResponseEntity initializeReferenceBetweenActorAndEvent() {
        //  localhost:8098/api/v1/person/initializeReferenceBetweenActorAndEvent
        personService.initializeReferenceBetweenActorAndEvent();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fillPersonTableFromScpaper")
    public ResponseEntity fillPersonTableFromScpaper() {
        // localhost:8098/api/v1/person/fillPersonTableFromScpaper
        personService.fillPersonTableFromScpaper();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenAuthorAndScpaper")
    public ResponseEntity initializeReferenceBetweenAuthorAndScpaper() {
        //  localhost:8098/api/v1/person/initializeReferenceBetweenAuthorAndScpaper
        personService.initializeReferenceBetweenAuthorAndScpaper();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fillPersonTableFromIsource")
    public ResponseEntity fillPersonTableFromIsource() {
        // localhost:8098/api/v1/person/fillPersonTableFromIsource
        personService.fillPersonTableFromIsource();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenModeratorAndIsource")
    public ResponseEntity initializeReferenceBetweenModeratorAndIsource() {
        //  localhost:8098/api/v1/person/initializeReferenceBetweenModeratorAndIsource
        personService.initializeReferenceBetweenModeratorAndIsource();
        return ResponseEntity.ok().build();
    }


    @GetMapping("/fillPersonTableFromOrg")
    public ResponseEntity fillPersonTableFromOrg() {
        // localhost:8098/api/v1/person/fillPersonTableFromOrg
        personService.fillPersonTableFromOrg();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenActorAndOrg")
    public ResponseEntity initializeReferenceBetweenActorAndOrg() {
        //  localhost:8098/api/v1/person/initializeReferenceBetweenActorAndOrg
        personService.initializeReferenceBetweenActorAndOrg();
        return ResponseEntity.ok().build();
    }*/
    ///////////////////////
}
