package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.Isource;
import com.hellokoding.springboot.restful.service.IsourceService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/isource/")
@Data
@Slf4j
public class IsourceAPI {

    private final IsourceService isourceService;

    @GetMapping
    public ResponseEntity<List<Isource>> findAll() {
        return ResponseEntity.ok(isourceService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Isource scpaper) {
        return ResponseEntity.ok(isourceService.save(scpaper));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Isource> findById(@PathVariable Integer id) {
        Optional<Isource> stock = isourceService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Isource> update(@PathVariable Integer id, @Valid @RequestBody Isource scpaper) {
        if (!isourceService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(isourceService.save(scpaper));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (!isourceService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        isourceService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
