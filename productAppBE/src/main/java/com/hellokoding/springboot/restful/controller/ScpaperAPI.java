package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.Scpaper;
import com.hellokoding.springboot.restful.service.ScpaperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/scpaper")
@RequiredArgsConstructor
@Slf4j

public class ScpaperAPI {
    private final ScpaperService scpaperService;

    @GetMapping
    public ResponseEntity<List<Scpaper>> findAll() {
        return ResponseEntity.ok(scpaperService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Scpaper scpaper) {
        return ResponseEntity.ok(scpaperService.save(scpaper));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scpaper> findById(@PathVariable Integer id) {
        Optional<Scpaper> stock = scpaperService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Scpaper> update(@PathVariable Integer id, @Valid @RequestBody Scpaper scpaper) {
        if (!scpaperService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(scpaperService.save(scpaper));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (!scpaperService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        scpaperService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
