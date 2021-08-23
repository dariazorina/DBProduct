package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.Status;
import com.hellokoding.springboot.restful.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/status")
@Slf4j
@RequiredArgsConstructor

public class StatusAPI {

    private final StatusService statusService;

    @GetMapping
    public ResponseEntity<List<Status>> findAll(@RequestParam(name = "q", required = false) String q) {
        if (!StringUtils.isEmpty(q)) {
            return ResponseEntity.ok(statusService.search(q));

        } else {
            return ResponseEntity.ok(statusService.findAll());
        }
    }

//    public ResponseEntity<List<ArticleDto>> findAll() {
//        List<ArticleDto> all = articleService.findAll();
//        return ResponseEntity.ok(all);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> findById(@PathVariable Integer id) {
        Optional<Status> stock = statusService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

}
