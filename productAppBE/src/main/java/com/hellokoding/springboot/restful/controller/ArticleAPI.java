package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.model.Attachment;
import com.hellokoding.springboot.restful.model.dto.ArticleDto;
import com.hellokoding.springboot.restful.service.ArticleService;
import com.hellokoding.springboot.restful.service.attachments.AttachmentService;
import com.hellokoding.springboot.restful.service.dto.AttachmentDTO;
import com.hellokoding.springboot.restful.service.dto.EntityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/article")
@Slf4j
@RequiredArgsConstructor

public class ArticleAPI {

    private final ArticleService articleService;
    private final AttachmentService attachmentService;

    //api/v1/article/search?title=title&hash=hash
    @GetMapping("/search")
    public ResponseEntity<List<ArticleDto>> search(@RequestParam(name = "title", required = false) String title,
                                                   @RequestParam(name = "hash", required = false) String hash,
                                                   @RequestParam(name = "author", required = false) String author,
                                                   @RequestParam(name = "language", required = false) String lang,
                                                   @RequestParam(name = "description", required = false) String descr,
                                                   @RequestParam(name = "status", required = false) List<Integer> status,
                                                   @RequestParam(name = "startDate", required = false) String startDate,
                                                   @RequestParam(name = "endDate", required = false) String endDate) throws ParseException {

        List<ArticleDto> searchResult = articleService.search(title, hash, author, lang, descr, status, startDate, endDate);
        return ResponseEntity.ok(searchResult);
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> findAll() {
        List<ArticleDto> all = articleService.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping
    public ResponseEntity<Article> create(@Valid @RequestBody ArticleDto article) {
        Article art = articleService.save(article);

        System.out.println("SAVED ARTICLE");
        System.out.println(art);

        return ResponseEntity.ok(art);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> findById(@PathVariable Integer id) {
        Optional<ArticleDto> stock = articleService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> update(@PathVariable Integer id, @Valid @RequestBody ArticleDto article) {
        article.setId(id);
        if (!articleService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(articleService.save(article));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (!articleService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        articleService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/attachment/{id}")
    public ResponseEntity<Integer> handleFileUpload(@PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        Integer attachmentId = attachmentService.createAttachment(EntityType.ARTICLE, id, Instant.now(), "admin", file.getOriginalFilename(), file.getBytes());
//        if (attachmentId == 0) return ResponseEntity.ok(attachmentId); //todo?
        return ResponseEntity.ok(attachmentId);
    }

    @GetMapping("/attachments/{id}")
    public ResponseEntity<List<AttachmentDTO>> getAttachments(@PathVariable Integer id) {
        List<AttachmentDTO> all = attachmentService.getAttachments(EntityType.ARTICLE, id);
        return ResponseEntity.ok(all);
    }
}
