package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.OrgType;
import com.hellokoding.springboot.restful.service.OrgTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orgtype")
@Slf4j
@RequiredArgsConstructor

public class OrgTypeAPI {

    private final OrgTypeService orgTypeService;

    @GetMapping("/search")
    public ResponseEntity<List<OrgType>> search(@RequestParam(name = "orgtype") String hash) {
        List<OrgType> search = orgTypeService.search(hash);
        return ResponseEntity.ok(search);
    }

    @GetMapping
    public ResponseEntity<List<OrgType>> findAll(@RequestParam(name = "q", required = false) String q) {
        if (!StringUtils.isEmpty(q)) {
            return ResponseEntity.ok(orgTypeService.search(q));

        } else {
            return ResponseEntity.ok(orgTypeService.findAll());
        }
    }

    @GetMapping("/leafs")
    public ResponseEntity<List<OrgType>> findLeafs(@RequestParam(name = "parentid") String parentId) {
        List<OrgType> search = orgTypeService.findLeafs(Integer.valueOf(parentId));
        return ResponseEntity.ok(search);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrgType> findById(@PathVariable Integer id) {
        Optional<OrgType> stock = orgTypeService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }
}
