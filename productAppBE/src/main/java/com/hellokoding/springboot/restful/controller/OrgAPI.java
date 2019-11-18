package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.Org;
import com.hellokoding.springboot.restful.service.OrgService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/org/")
@Data
@Slf4j
public class OrgAPI {

    private final OrgService orgService;

    @GetMapping
    public ResponseEntity<List<Org>> findAll() {
        return ResponseEntity.ok(orgService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Org org) {
        return ResponseEntity.ok(orgService.save(org));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Org> findById(@PathVariable Integer id) {
        Optional<Org> stock = orgService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Org> update(@PathVariable Integer id, @Valid @RequestBody Org org) {
        if (!orgService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(orgService.save(org));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (!orgService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        orgService.deleteById(id);
        return ResponseEntity.ok().build();
    }


//    ////////utils////
//    @GetMapping("/addOrgFromEventTableToOrgTable")
//    public ResponseEntity addOrgFromEventTableToOrgTable() {
//        //  localhost:8098/api/v1/org/addOrgFromEventTableToOrgTable
//        orgService.addOrgFromEventTableToOrgTable();
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping("/changeOrgTitleToOrgId")
//    public ResponseEntity changeOrgTitleToOrgId() {
//        //  localhost:8098/api/v1/org/changeOrgTitleToOrgId
//        orgService.changeOrgTitleToOrgId();
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/initializeReferenceBetweenOrgAndEvent")
//    public ResponseEntity initializeReferenceBetweenOrgAndEvent() {
//        //  localhost:8098/api/v1/org/initializeReferenceBetweenOrgAndEvent
//        orgService.initializeReferenceBetweenOrgAndEvent();
//        return ResponseEntity.ok().build();
//    }

}
