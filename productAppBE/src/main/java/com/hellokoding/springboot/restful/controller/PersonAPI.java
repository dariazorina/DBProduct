package com.hellokoding.springboot.restful.controller;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hellokoding.springboot.restful.model.dto.NewPersonDto;
import com.hellokoding.springboot.restful.model.dto.PersonDto;
import com.hellokoding.springboot.restful.service.PersonService;
import com.hellokoding.springboot.restful.service.attachments.AttachmentService;
import com.hellokoding.springboot.restful.service.dto.AttachmentDTO;
import com.hellokoding.springboot.restful.service.dto.EntityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@JsonSerialize
@RestController
@RequestMapping("/api/v1/person")
@Slf4j
@RequiredArgsConstructor
public class PersonAPI {
    private final PersonService personService;
    private final AttachmentService attachmentService;

    @GetMapping("/search")
    public ResponseEntity<List<PersonDto>> search(@RequestParam(name = "q", required = true) String q) {
        List<PersonDto> search = personService.search(q);
        return ResponseEntity.ok(search);
    }

    @GetMapping
    public ResponseEntity<List<NewPersonDto>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody NewPersonDto person) {
        return ResponseEntity.ok(personService.save(person));
    }

    @PostMapping("/ids")
    public ResponseEntity<List<NewPersonDto>> getPersonsByIds(@Valid @RequestBody List<Integer> idList) {
        return ResponseEntity.ok(personService.findByIds(idList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewPersonDto> findById(@PathVariable Integer id) {
        Optional<NewPersonDto> stock = personService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id, @Valid @RequestBody NewPersonDto person) {
        person.setId(id);
        if (!personService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(personService.save(person));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (!personService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/attachment/{id}")
    public ResponseEntity<Integer> handleFileUpload(@PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        Integer attachmentId = attachmentService.createAttachment(EntityType.PERSON, id, Instant.now(), "admin", file.getOriginalFilename(), file.getBytes());
//        if (attachmentId == 0) return ResponseEntity.ok(attachmentId); //todo?
        return ResponseEntity.ok(attachmentId);
    }

    @GetMapping("/attachments/{id}")
    public ResponseEntity<List<AttachmentDTO>> getAttachments(@PathVariable Integer id) {
        List<AttachmentDTO> all = attachmentService.getAttachments(EntityType.PERSON, id);
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
        AttachmentDTO attachmentDTO = attachmentService.getAttachment(EntityType.PERSON, entityId, id);
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
    }

    @GetMapping("/downloadAttachment")
    public ResponseEntity<InputStreamResource> downloadAttachment(@RequestParam(name = "entityId", required = true) Integer entityId, @RequestParam(name = "id", required = true) Integer id) {

        HttpHeaders headers = new HttpHeaders();
        ByteArrayInputStream bis = null;
        AttachmentDTO attachmentDTO = attachmentService.getAttachment(EntityType.PERSON, entityId, id);
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



    //////////////////////utils/////////////////////////////////////////
/*    @GetMapping("/fillPersonTableFromArticle")
    public ResponseEntity fillPersonTableFromArticle() {
        // localhost:8098/api/v1/person/fillPersonTableFromArticle
        personService.fillPersonTableFromArticle();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenAuthorAndArticle")
    public ResponseEntity initializeReferenceBetweenAuthorAndArticle() {
        //  localhost:8098/api/v1/person/initializeReferenceBetweenAuthorAndArticle
        personService.initializeReferenceBetweenAuthorAndArticle();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fillPersonTableFromEvent")
    public ResponseEntity fillPersonTableFromEvent() {
        // localhost:8098/api/v1/person/fillPersonTableFromEvent
        personService.fillPersonTableFromEvent();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenActorAndEvent")
    public ResponseEntity initializeReferenceBetweenActorAndEvent() {
        //  localhost:8098/api/v1/person/initializeReferenceBetweenActorAndEvent
        personService.initializeReferenceBetweenActorAndEvent();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fillPersonTableFromScpaper")
    public ResponseEntity fillPersonTableFromScpaper() {
        // localhost:8098/api/v1/person/fillPersonTableFromScpaper
        personService.fillPersonTableFromScpaper();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenAuthorAndScpaper")
    public ResponseEntity initializeReferenceBetweenAuthorAndScpaper() {
        //  localhost:8098/api/v1/person/initializeReferenceBetweenAuthorAndScpaper
        personService.initializeReferenceBetweenAuthorAndScpaper();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fillPersonTableFromIsource")
    public ResponseEntity fillPersonTableFromIsource() {
        // localhost:8098/api/v1/person/fillPersonTableFromIsource
        personService.fillPersonTableFromIsource();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenModeratorAndIsource")
    public ResponseEntity initializeReferenceBetweenModeratorAndIsource() {
        //  localhost:8098/api/v1/person/initializeReferenceBetweenModeratorAndIsource
        personService.initializeReferenceBetweenModeratorAndIsource();
        return ResponseEntity.ok().build();
    }


    @GetMapping("/fillPersonTableFromOrg")
    public ResponseEntity fillPersonTableFromOrg() {
        // localhost:8098/api/v1/person/fillPersonTableFromOrg
        personService.fillPersonTableFromOrg();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/initializeReferenceBetweenActorAndOrg")
    public ResponseEntity initializeReferenceBetweenActorAndOrg() {
        //  localhost:8098/api/v1/person/initializeReferenceBetweenActorAndOrg
        personService.initializeReferenceBetweenActorAndOrg();
        return ResponseEntity.ok().build();
    }*/
    ///////////////////////
}
