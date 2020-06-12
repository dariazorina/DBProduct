package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.Movement;
import com.hellokoding.springboot.restful.service.MovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/movement")

public class MovementAPI {

    private final MovementService movementService;
    public static final String MOVEMENT_CODE_OR_NAME_IS_NOT_UNIQUE = "Movement code or name is not unique.";

    @GetMapping
    public ResponseEntity<List<Movement>> findAll() {
        return ResponseEntity.ok(movementService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Movement movement) {
        try {
            return ResponseEntity.ok(movementService.save(movement));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(MOVEMENT_CODE_OR_NAME_IS_NOT_UNIQUE);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movement> findById(@PathVariable Integer id) {
        Optional<Movement> stock = movementService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id, @Valid @RequestBody Movement movement) {
        if (!movementService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(movementService.save(movement));
        } catch (UniqueConstraintViolationException ex){
            return ResponseEntity.badRequest().body(MOVEMENT_CODE_OR_NAME_IS_NOT_UNIQUE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (!movementService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        movementService.deleteById(id);
        return ResponseEntity.ok().build();
    }

/*    //////////////////////utils/////////////////////////////////////////
    @GetMapping("/initializeReferenceBetweenMovementAndIsource")
    public ResponseEntity initializeReferenceBetweenMovementAndIsource() {
        //  localhost:8098/api/v1/movement/initializeReferenceBetweenMovementAndIsource
        movementService.initializeReferenceBetweenMovementAndIsource();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenMovementAndOrg")
    public ResponseEntity initializeReferenceBetweenMovementAndOrg() {
        //  localhost:8098/api/v1/movement/initializeReferenceBetweenMovementAndOrg
        movementService.initializeReferenceBetweenMovementAndOrg();
        return ResponseEntity.ok().build();
    }*/
}
