package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.HashTag;
import com.hellokoding.springboot.restful.service.HashTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/hashtag")
@Slf4j
@RequiredArgsConstructor

public class HashTagAPI {

    private final HashTagService hashTagService;

    @GetMapping("/search")
    public ResponseEntity<List<HashTag>> search(@RequestParam(name = "hash") String hash) {
        List<HashTag> search = hashTagService.search(hash);
        return ResponseEntity.ok(search);
    }

    @GetMapping
    public ResponseEntity<List<HashTag>> findAll(@RequestParam(name = "q", required = false) String q) {
        if (!StringUtils.isEmpty(q)) {
            return ResponseEntity.ok(hashTagService.search(q));

        } else {
            return ResponseEntity.ok(hashTagService.findAll());
        }
    }

    @GetMapping("/leafs")
    public ResponseEntity<List<HashTag>> findLeafs(@RequestParam(name = "parentid") String parentId) {
        List<HashTag> search = hashTagService.findLeafs(Integer.valueOf(parentId));
        return ResponseEntity.ok(search);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashTag> findById(@PathVariable Integer id) {
        Optional<HashTag> stock = hashTagService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody HashTag hashTag) {
        HashTag saved = hashTagService.save(hashTag);
        if (saved != null) {
            return ResponseEntity.ok(saved);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashTag> update(@PathVariable Integer id, @Valid @RequestBody HashTag hashTag) {
        hashTag.setId(id);
        if (!hashTagService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(hashTagService.save(hashTag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {

        Optional<HashTag> hashtagToDelete = hashTagService.findById(id);

        if (!hashtagToDelete.isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.badRequest().build();
        }
        hashTagService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    ////utils////
/*    @GetMapping("/fillHashTagTableFromArticle")
    public ResponseEntity fillHashTagTableFromArticle() {
        // localhost:8098/api/v1/hashtag/fillHashTagTableFromArticle
        hashTagService.fillHashTagTableFromArticle();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenHashTagAndArticle")
    public ResponseEntity initializeReferenceBetweenHashTagAndArticle() {
        //  localhost:8098/api/v1/hashtag/initializeReferenceBetweenHashTagAndArticle
        hashTagService.initializeReferenceBetweenHashTagAndArticle();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fillHashTagTableFromEvent")
    public ResponseEntity fillHashTagTableFromEvent() {
        // localhost:8098/api/v1/hashtag/fillHashTagTableFromEvent
        hashTagService.fillHashTagTableFromEvent();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenHashTagAndEvent")
    public ResponseEntity initializeReferenceBetweenHashTagAndEvent() {
        //  localhost:8098/api/v1/hashtag/initializeReferenceBetweenHashTagAndEvent
        hashTagService.initializeReferenceBetweenHashTagAndEvent();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fillHashTagTableFromScpaper")
    public ResponseEntity fillHashTagTableFromScpaper() {
        // localhost:8098/api/v1/hashtag/fillHashTagTableFromScpaper
        hashTagService.fillHashTagTableFromScpaper();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenHashTagAndScpaper")
    public ResponseEntity initializeReferenceBetweenHashTagAndScpaper() {
        //  localhost:8098/api/v1/hashtag/initializeReferenceBetweenHashTagAndScpaper
        hashTagService.initializeReferenceBetweenHashTagAndScpaper();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fillHashTagTableFromIsource")
    public ResponseEntity fillHashTagTableFromIsource() {
        // localhost:8098/api/v1/hashtag/fillHashTagTableFromIsource
        hashTagService.fillHashTagTableFromIsource();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenHashTagAndIsource")
    public ResponseEntity initializeReferenceBetweenHashTagAndIsource() {
        //  localhost:8098/api/v1/hashtag/initializeReferenceBetweenHashTagAndIsource
        hashTagService.initializeReferenceBetweenHashTagAndIsource();
        return ResponseEntity.ok().build();
    }*/
    /////// ////

}
