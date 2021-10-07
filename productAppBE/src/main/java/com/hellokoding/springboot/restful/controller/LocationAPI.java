package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.Location;
import com.hellokoding.springboot.restful.model.dto.IdContentDto;
//import com.hellokoding.springboot.restful.model.dto.LocationDto;
import com.hellokoding.springboot.restful.service.LocationService;
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
public class LocationAPI {

    public static final String COUNTRY_CODE_OR_NAME_IS_NOT_UNIQUE = "Country code or name is not unique.";
    private final LocationService locationService;

    @GetMapping("/search")
    public ResponseEntity<List<IdContentDto>> search(@RequestParam(name = "q", required = true) String q) {
        List<IdContentDto> search = locationService.search(q);
        return ResponseEntity.ok(search);
    }
    @GetMapping
    public ResponseEntity<List<Location>> findAll() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @PostMapping("/ids")
    public ResponseEntity<List<IdContentDto>> getLocationsByIds(@Valid @RequestBody List<Integer> idList) {
        return ResponseEntity.ok(locationService.findByIds(idList));
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Location location) {
        try {
            return ResponseEntity.ok(locationService.save(location));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(COUNTRY_CODE_OR_NAME_IS_NOT_UNIQUE);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> findById(@PathVariable Integer id) {
        Optional<Location> stock = locationService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id, @Valid @RequestBody Location location) {
        if (!locationService.findById(id).isPresent()) {
            log.error("Id {} is not existed", id);
            ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(locationService.save(location));
        } catch (UniqueConstraintViolationException ex) {
            return ResponseEntity.badRequest().body(COUNTRY_CODE_OR_NAME_IS_NOT_UNIQUE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (!locationService.findById(id).isPresent()) {
            log.error("Id {} is not existed", id);
            ResponseEntity.badRequest().build();
        }
        locationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
