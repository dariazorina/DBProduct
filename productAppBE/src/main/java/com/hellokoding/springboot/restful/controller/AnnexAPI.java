package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.Annex;
import com.hellokoding.springboot.restful.service.AnnexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/annex")
@Slf4j
@RequiredArgsConstructor
public class AnnexAPI {
    private final AnnexService annexService;

    @GetMapping
    public ResponseEntity<List<Annex>> findAll() {
        return ResponseEntity.ok(annexService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Annex annex) {
        return ResponseEntity.ok(annexService.save(annex));
    }

//    //utils
//    @GetMapping("/fillAnnexTableFromEvent")
//    public ResponseEntity fillAnnexTableFromEvent() {
//        // localhost:8098/api/v1/annex/fillAnnexTableFromEvent
//        annexService.fillAnnexTableFromEvent();
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping("/initializeReferenceBetweenAnnexAndEvent")
//    public ResponseEntity initializeReferenceBetweenAnnexAndEvent() {
//        //  localhost:8098/api/v1/annex/initializeReferenceBetweenAnnexAndEvent
//        annexService.initializeReferenceBetweenAnnexAndEvent();
//        return ResponseEntity.ok().build();
//    }
    ///////////////////////
}
