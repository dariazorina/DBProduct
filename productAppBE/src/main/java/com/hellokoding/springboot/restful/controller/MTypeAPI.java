package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.MaterialType;
import com.hellokoding.springboot.restful.service.MTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/mtype")
@Slf4j
@RequiredArgsConstructor

public class MTypeAPI {

    private final MTypeService mtypeService;

    @GetMapping("/search")
    public ResponseEntity<List<MaterialType>> search(@RequestParam(name = "mtype") String hash) {
        List<MaterialType> search = mtypeService.search(hash);
        return ResponseEntity.ok(search);
    }

    @GetMapping
    public ResponseEntity<List<MaterialType>> findAll(@RequestParam(name = "q", required = false) String q) {
        if (!StringUtils.isEmpty(q)) {
            return ResponseEntity.ok(mtypeService.search(q));

        } else {
            return ResponseEntity.ok(mtypeService.findAll());
        }
    }

    @GetMapping("/leafs")
    public ResponseEntity<List<MaterialType>> findLeafs(@RequestParam(name = "parentid") String parentId) {
        List<MaterialType> search = mtypeService.findLeafs(Integer.valueOf(parentId));
        return ResponseEntity.ok(search);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialType> findById(@PathVariable Integer id) {
        Optional<MaterialType> stock = mtypeService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody MaterialType mtype) {
        MaterialType saved = mtypeService.save(mtype);
        if (saved != null) {
            return ResponseEntity.ok(saved);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialType> update(@PathVariable Integer id, @Valid @RequestBody MaterialType mtype) {
        mtype.setId(id);
        if (!mtypeService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mtypeService.save(mtype));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {

        Optional<MaterialType> mtypeToDelete = mtypeService.findById(id);
        if (!mtypeToDelete.isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.badRequest().build();
        }
        mtypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
