package com.hellokoding.springboot.restful.controller;
import com.hellokoding.springboot.restful.model.Language;
import com.hellokoding.springboot.restful.service.LanguageService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/language/")
@Data
@Slf4j
public class LanguageAPI {

    private final LanguageService languageService;

    @GetMapping
    public ResponseEntity<List<Language>> findAll() {
        return ResponseEntity.ok(languageService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Language language) {
        return ResponseEntity.ok(languageService.save(language));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Language> findById(@PathVariable Long id) {
        Optional<Language> stock = languageService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Language> update(@PathVariable Long id, @Valid @RequestBody Language language) {
        if (!languageService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(languageService.save(language));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (!languageService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        languageService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //////////////////////utils/////////////////////////////////////////
    @GetMapping("/initializeReferenceBetweenLanguageAndIsource")
    public ResponseEntity initializeReferenceBetweenLanguageAndIsource() {
        //  localhost:8098/api/v1/language/initializeReferenceBetweenLanguageAndIsource
        languageService.initializeReferenceBetweenLanguageAndIsource();
        return ResponseEntity.ok().build();
    }

}
