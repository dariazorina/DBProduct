package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.Country;
import com.hellokoding.springboot.restful.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/country")
@Slf4j
@RequiredArgsConstructor
public class CountryAPI {

    public static final String COUNTRY_CODE_OR_NAME_IS_NOT_UNIQUE = "Country code or name is not unique.";
    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<Country>> findAll() {
        return ResponseEntity.ok(countryService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Country country) {
        try {
            return ResponseEntity.ok(countryService.save(country));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(COUNTRY_CODE_OR_NAME_IS_NOT_UNIQUE);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> findById(@PathVariable Integer id) {
        Optional<Country> stock = countryService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id, @Valid @RequestBody Country country) {
        if (!countryService.findById(id).isPresent()) {
            log.error("Id {} is not existed", id);
            ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(countryService.save(country));
        } catch (UniqueConstraintViolationException ex) {
            return ResponseEntity.badRequest().body(COUNTRY_CODE_OR_NAME_IS_NOT_UNIQUE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (!countryService.findById(id).isPresent()) {
            log.error("Id {} is not existed", id);
            ResponseEntity.badRequest().build();
        }
        countryService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
