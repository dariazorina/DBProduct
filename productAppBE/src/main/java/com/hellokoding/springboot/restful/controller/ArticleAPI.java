package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.model.ConnectionType;
import com.hellokoding.springboot.restful.model.dto.ArticleDto;
import com.hellokoding.springboot.restful.model.dto.IdContentDto;
import com.hellokoding.springboot.restful.model.dto.ItemConnectionDto;
import com.hellokoding.springboot.restful.model.dto.NameConnectionDto;
import com.hellokoding.springboot.restful.service.ArticleService;
import com.hellokoding.springboot.restful.service.ConnectionTypeService;
import com.hellokoding.springboot.restful.service.attachments.AttachmentService;
import com.hellokoding.springboot.restful.service.dto.AttachmentDTO;
import com.hellokoding.springboot.restful.service.dto.EntityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
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
    private final ConnectionTypeService ctypeService;

    //api/v1/article/search?title=title&hash=hash
    @GetMapping("/filter")
    public ResponseEntity<List<ArticleDto>> filter(@RequestParam(name = "title", required = false) List<String> title,
                                                   @RequestParam(name = "hash", required = false) List<String> hash,
                                                   @RequestParam(name = "author", required = false) List<String> author,
                                                   @RequestParam(name = "org", required = false) List<String> org,
                                                   @RequestParam(name = "location", required = false) List<String> location,
                                                   @RequestParam(name = "language", required = false) List<String> lang,
                                                   @RequestParam(name = "description", required = false) String descr,
                                                   @RequestParam(name = "text", required = false) String text,
                                                   @RequestParam(name = "miscellany", required = false) List<String> misc,
                                                   @RequestParam(name = "status", required = false) List<Integer> status,
                                                   @RequestParam(name = "startDate", required = false) String startDate,
                                                   @RequestParam(name = "endDate", required = false) String endDate,
                                                   @RequestParam(name = "mov", required = false) Integer movement) throws ParseException {

        List<ArticleDto> searchResult = articleService.filter(title, hash, author, org, location, lang, descr, text, misc, status, startDate, endDate, movement);
        return ResponseEntity.ok(searchResult);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArticleDto>> search(@RequestParam(name = "description", required = false) String descr,
                                                   @RequestParam(name = "text", required = false) String text,
                                                   @RequestParam(name = "status", required = false) List<Integer> status,
                                                   @RequestParam(name = "startDate", required = false) String startDate,
                                                   @RequestParam(name = "endDate", required = false) String endDate,
                                                   @RequestParam(name = "mov", required = false) Integer movement) throws ParseException {

        List<ArticleDto> searchResult = articleService.search(descr, text, status, startDate, endDate, movement);
        return ResponseEntity.ok(searchResult);
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> findAll() {
        List<ArticleDto> all = articleService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/searchMaterial")
    public ResponseEntity<List<IdContentDto>> searchMaterial(@RequestParam(name = "q", required = true) String q,
                                                             @RequestParam(name = "mov", required = true) Integer mov) {
        List<IdContentDto> search = articleService.searchMaterial(q, mov);
        return ResponseEntity.ok(search);
    }

    @GetMapping("/searchMaterialById")
    public ResponseEntity<List<IdContentDto>> searchMaterialById(@RequestParam(name = "id", required = true) String id) {
        Integer intId = Integer.parseInt(id);
        List<IdContentDto> search = articleService.searchMaterialById(intId);
        return ResponseEntity.ok(search);
    }

    @PostMapping("/ids")
    public ResponseEntity<List<IdContentDto>> getMaterialsByIds(@Valid @RequestBody List<Integer> idList) {
        return ResponseEntity.ok(articleService.findByIds(idList));
    }

    @PostMapping("/symmids/{id}")
    public ResponseEntity<List<NameConnectionDto>> getMaterialsByIdsAndSymmetrically(@PathVariable Integer id, @Valid @RequestBody List<Integer> idList) {
        //List<IdContentDto> rrr = orgService.findByIdsAndSymmetrically(idList, id);
        return ResponseEntity.ok(articleService.findByIdsAndSymmetrically(idList, id));
    }

    @PostMapping("/connectionTypes")
    public ResponseEntity<List<ConnectionType>> getConnectionTypes() {
        return ResponseEntity.ok(ctypeService.findAll());
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

    @DeleteMapping("/attachment")
    public ResponseEntity deleteAttachment(@RequestParam(name = "entityId", required = true) Integer entityId,
                                           @RequestParam(name = "fileId", required = true) Integer fileId,
                                           @RequestParam(name = "fileName", required = true) String fileName) {

        boolean res = attachmentService.deleteAttachment(EntityType.ARTICLE, entityId, fileId, fileName);
        return ResponseEntity.ok(res);
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

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    @GetMapping("/attachment")
    public ResponseEntity<InputStreamResource> getAttachment(@RequestParam(name = "entityId", required = true) Integer entityId, @RequestParam(name = "id", required = true) Integer id) {

        HttpHeaders headers = new HttpHeaders();
        ByteArrayInputStream bis = null;
        AttachmentDTO attachmentDTO = attachmentService.getAttachment(EntityType.ARTICLE, entityId, id);
        MediaType type = MediaType.APPLICATION_OCTET_STREAM;  //APPLICATION_PROBLEM_JSON;
        Optional<String> extension;

        try{
//            File uFile = null;
//            uFile = maintainFileService.getDocument(123456L);
            bis = new ByteArrayInputStream(attachmentDTO.getContent());
            String FN =  attachmentDTO.getName().replaceAll(",", ".");
            extension = getExtensionByStringHandling(FN);

            String headerView = "inline; filename=" + FN;
            headers.add("Content-Disposition", headerView);

            if (extension.isPresent()) {
                switch (extension.get().toLowerCase()) {
                    case "pdf": {
                        type = MediaType.APPLICATION_PDF;
                        break;
                    }
                    case "png": {
                        type = MediaType.IMAGE_PNG;
                        break;
                    }
                    case "jpg":
                    case "jpeg": {
                        type = MediaType.IMAGE_JPEG;
                        break;
                    }
                    case "gif": {
                        type = MediaType.IMAGE_GIF;
                        break;
                    }
                    case "txt": {
                        type = MediaType.TEXT_PLAIN;
                        break;
                    }

                    case "fb2":
                    case "htm":
                    case "html":{
                        type = MediaType.TEXT_HTML;
                        break;
                    }
                }
            }
        }
        catch(Exception e){
            System.out.printf("Exception", e);
        }

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(type)
                .body(new InputStreamResource(bis));


//    public ResponseEntity<Blob> getAttachment(@RequestParam(name = "entityId", required = true) Integer entityId, @RequestParam(name = "id", required = true) Integer id) {
//    public ResponseEntity<byte[]> getAttachment(@RequestParam(name = "entityId", required = true) Integer entityId, @RequestParam(name = "id", required = true) Integer id) {

//        Blob attachment = attachmentService.getAttachment(EntityType.ARTICLE, entityId, id);
//        byte[] attachment = attachmentService.getAttachment(EntityType.ARTICLE, entityId, id);
//        return ResponseEntity.ok(attachment);
    }

    @GetMapping("/downloadAttachment")
    public ResponseEntity<InputStreamResource> downloadAttachment(@RequestParam(name = "entityId", required = true) Integer entityId, @RequestParam(name = "id", required = true) Integer id) {

        HttpHeaders headers = new HttpHeaders();
        ByteArrayInputStream bis = null;
        AttachmentDTO attachmentDTO = attachmentService.getAttachment(EntityType.ARTICLE, entityId, id);
//        MediaType type = MediaType.APPLICATION_OCTET_STREAM;  //APPLICATION_PROBLEM_JSON;

        try{
            bis = new ByteArrayInputStream(attachmentDTO.getContent());
            String FN =  attachmentDTO.getName().replaceAll(",", ".");

            String headerView = "attachment; filename=" + FN;
            headers.add("Content-Disposition", headerView);
//            headers.add("Content-Description", "File Transfer");
        }
        catch(Exception e){
            System.out.printf("Exception", e);
        }
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(bis));
    }
}
