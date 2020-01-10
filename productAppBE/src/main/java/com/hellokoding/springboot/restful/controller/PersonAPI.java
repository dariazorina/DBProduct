package com.hellokoding.springboot.restful.controller;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hellokoding.springboot.restful.model.Person;
import com.hellokoding.springboot.restful.model.dto.PersonDto;
import com.hellokoding.springboot.restful.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@JsonSerialize
@RestController
@RequestMapping("/api/v1/person")
@Slf4j
@RequiredArgsConstructor
public class PersonAPI {
    private final PersonService personService;

    @GetMapping("/search")
    public ResponseEntity<List<PersonDto>> search(@RequestParam(name = "q", required = true) String q) {
        List<PersonDto> search = personService.search(q);
        return ResponseEntity.ok(search);
    }

    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @PostMapping
    public ResponseEntity
    create(@Valid @RequestBody Person person) {
        return ResponseEntity.ok(personService.save(person));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Integer id) {
        Optional<Person> stock = personService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable Integer id, @Valid @RequestBody Person person) {
        person.setId(id);
        if (!personService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(personService.save(person));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (!personService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        personService.deleteById(id);
        return ResponseEntity.ok().build();
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
