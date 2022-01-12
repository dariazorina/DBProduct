package com.hellokoding.springboot.restful.controller;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hellokoding.springboot.restful.model.dto.*;
//import com.hellokoding.springboot.restful.model.dto.PersonDto;
import com.hellokoding.springboot.restful.service.PersonService;
import com.hellokoding.springboot.restful.service.attachments.AttachmentService;
import com.hellokoding.springboot.restful.service.dto.AttachmentDTO;
import com.hellokoding.springboot.restful.service.dto.EntityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
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

    @GetMapping("/searchBySurname")
    public ResponseEntity<List<IdContentDto>> search(@RequestParam(name = "q", required = true) String q) { //},
//                                                     @RequestParam(name = "mov", required = true) Integer mov) {
        List<IdContentDto> search = personService.searchBySurname(q);   //used for ex in material creation
        return ResponseEntity.ok(search);
    }


    @GetMapping("/filter")
    public ResponseEntity<PagedDataDto> filter(@RequestParam(name = "hash", required = false) List<String> hash,
                                                                @RequestParam(name = "surname", required = false) List<String> surname,
                                                                @RequestParam(name = "org", required = false) List<String> org,
                                                                @RequestParam(name = "location", required = false) List<String> location,
                                                                @RequestParam(name = "mov", required = false) List<Integer> movement,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "3") int size) {

        return ResponseEntity.ok(personService.filter(hash, surname, org, location, movement, page, size));
    }


//    @GetMapping
//    public ResponseEntity<PagedDataDto> findAll(@RequestParam(name = "mov", required = false) List<Integer> mov,
//                                                @RequestParam(defaultValue = "0") int page,
//                                                @RequestParam(defaultValue = "3") int size) {
//        return ResponseEntity.ok(personService.findAll(mov, page, size));
//    }


//    @GetMapping("/allQuant")
//    public ResponseEntity<Integer> findQuantityAllPersons(@RequestParam(name = "mov", required = false) List<Integer> mov) {
//        return ResponseEntity.ok(personService.getQuantityAllPersonsWithMovement(mov));
//    }

//    @GetMapping
//    public ResponseEntity<List<NewPersonDtoForMainList>> findAll(@RequestParam(name = "mov", required = false) List<Integer> mov) {
//        return ResponseEntity.ok(personService.findAll(mov));
//    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody NewPersonDto person) {
        return ResponseEntity.ok(personService.save(person));
    }

    @PostMapping("/ids")
    public ResponseEntity<List<IdContentDto>> getPersonsByIds(@Valid @RequestBody List<Integer> idList) {
        return ResponseEntity.ok(personService.findByIds(idList));
    }

    @PostMapping("/symmids/{id}")
    public ResponseEntity<List<NameConnectionDto>> getPersonsByIdsAndSymmetrically(@PathVariable Integer id) {
        return ResponseEntity.ok(personService.findByIdsAndSymmetrically(id));
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

    @PutMapping("/color/{id}")
    public ResponseEntity updateColor(@PathVariable Integer id, @Valid @RequestBody NewPersonDtoForMainList person) {
        person.setId(id);
        if (!personService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(personService.saveColor(person));
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

    @DeleteMapping("/attachmentPhoto/{id}")
    public ResponseEntity deleteAttachmentPhoto(@PathVariable Integer id) {
        boolean res = attachmentService.deleteAttachmentPhoto(EntityType.PERSON, id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/attachment")
    public ResponseEntity deleteAttachment(@RequestParam(name = "entityId", required = true) Integer entityId,
                                           @RequestParam(name = "fileId", required = true) Integer fileId,
                                           @RequestParam(name = "fileName", required = true) String fileName) {

        boolean res = attachmentService.deleteAttachment(EntityType.PERSON, entityId, fileId, fileName);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/attachment/{id}")
    public ResponseEntity<Integer> handleFileUpload(@PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        Integer attachmentId = attachmentService.createAttachment(EntityType.PERSON, id, Instant.now(), "admin", file.getOriginalFilename(), file.getBytes());
//        if (attachmentId == 0) return ResponseEntity.ok(attachmentId); //todo?
        return ResponseEntity.ok(attachmentId);
    }


    @PostMapping("/attachmentPhoto/{id}")
    public ResponseEntity<Integer> handlePhotoUpload(@PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        Integer attachmentId = attachmentService.createPhotoAttachment(EntityType.PERSON, id, Instant.now(), "admin", file.getOriginalFilename(), file.getBytes());
        return ResponseEntity.ok(attachmentId);
    }


    @GetMapping("/attachments/{id}")
    public ResponseEntity<List<AttachmentDTO>> getAttachments(@PathVariable Integer id) {
        List<AttachmentDTO> all = attachmentService.getAttachments(EntityType.PERSON, id);
        return ResponseEntity.ok(all);
    }


//    @GetMapping("/attachmentPhoto/{id}")
////    public ResponseEntity<AttachmentDTO> getAttachmentPhoto(@PathVariable Integer id) {
//    public ResponseEntity<InputStreamResource> getAttachmentPhoto(@PathVariable Integer id) {
//        AttachmentDTO photo = attachmentService.getAttachmentPhoto(EntityType.PERSON, id);
//
//        if (photo != null) {
//
//            HttpHeaders headers = new HttpHeaders();
//            ByteArrayInputStream bis = null;
//            MediaType type = MediaType.APPLICATION_OCTET_STREAM;  //APPLICATION_PROBLEM_JSON;
//            Optional<String> extension;
//
//            try {
//                bis = new ByteArrayInputStream(photo.getContent());
//                String FN = photo.getName().replaceAll(",", ".");
//                extension = getExtensionByStringHandling(FN);
//
//                String headerView = "inline; filename=" + FN;
//                headers.add("Content-Disposition", headerView);
//
//                if (extension.isPresent()) {
//                    switch (extension.get().toLowerCase()) {
//                        case "png": {
//                            type = MediaType.IMAGE_PNG;
//                            break;
//                        }
//                        case "jpg":
//                        case "jpeg": {
//                            type = MediaType.IMAGE_JPEG;
//                            break;
//                        }
//                        case "gif": {
//                            type = MediaType.IMAGE_GIF;
//                            break;
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                System.out.printf("Exception", e);
//            }
//
//            return ResponseEntity
//                    .ok()
//                    .headers(headers)
//                    .contentType(type)
//                    .body(new InputStreamResource(bis));
//
//        } else {
//            return ResponseEntity.badRequest().build();//  (HttpStatus.NOT_FOUND);
//        }
//    }


    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    @GetMapping("/attachment")  ///for preview in blank window
    public ResponseEntity<InputStreamResource> getAttachment(@RequestParam(name = "entityId", required = true) Integer entityId, @RequestParam(name = "id", required = true) Integer id) {

        HttpHeaders headers = new HttpHeaders();
        ByteArrayInputStream bis = null;
        AttachmentDTO attachmentDTO = attachmentService.getAttachment(EntityType.PERSON, entityId, id);
        MediaType type = MediaType.APPLICATION_OCTET_STREAM;  //APPLICATION_PROBLEM_JSON;
        Optional<String> extension;

        try {
//            File uFile = null;
//            uFile = maintainFileService.getDocument(123456L);
            bis = new ByteArrayInputStream(attachmentDTO.getContent());
            String FN = attachmentDTO.getName().replaceAll(",", ".");
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
                    case "html": {
                        type = MediaType.TEXT_HTML;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.printf("Exception", e);
        }

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(type)
                .body(new InputStreamResource(bis));
    }

//    @GetMapping("/attachmentPhoto/{entityId}")
//    public ResponseEntity<InputStreamResource> getAttachmentPhoto(@PathVariable Integer entityId) {
//
//        HttpHeaders headers = new HttpHeaders();
//        ByteArrayInputStream bis = null;
//
//        AttachmentDTO attachmentDTO = attachmentService.getAttachmentPhoto(EntityType.PERSON, entityId);
//        MediaType type = MediaType.IMAGE_JPEG;//APPLICATION_PROBLEM_JSON_UTF8; //APPLICATION_JSON_UTF8; //APPLICATION_OCTET_STREAM;  //APPLICATION_PROBLEM_JSON;
////        application/json; charset=utf-8
//        Optional<String> extension;
//
//        try {
//            bis = new ByteArrayInputStream(attachmentDTO.getContent());
//
//            String FN = attachmentDTO.getName().replaceAll(",", ".");
//            extension = getExtensionByStringHandling(FN);
//
//            String headerView = "inline; filename=" + FN;
//            headers.add("Content-Disposition", headerView);
//            headers.setContentType(MediaType.TEXT_PLAIN);
//
////            if (extension.isPresent()) {
////                switch (extension.get().toLowerCase()) {
////                    case "png": {
////                        type = MediaType.IMAGE_PNG;
////                        break;
////                    }
////                    case "jpg":
////                    case "jpeg": {
////                        type = MediaType.IMAGE_JPEG;
////                        break;
////                    }
////                    case "gif": {
////                        type = MediaType.IMAGE_GIF;
////                        break;
////                    }
////                }
////            }
//        } catch (Exception e) {
//            System.out.printf("Exception", e);
//        }
//
//        return new ResponseEntity<InputStreamResource>(new InputStreamResource(bis), headers, HttpStatus.OK);
////        return  ResponseEntity
////                .ok()
////                .headers(headers)
////                .contentType(type)
////                .body(new InputStreamResource(bis));
//    }


    @GetMapping("/attachmentPhoto/{entityId}")
    public ResponseEntity<String> getAttachmentPhoto(@PathVariable Integer entityId) {

        AttachmentDTO attachmentDTO = attachmentService.getAttachmentPhoto(EntityType.PERSON, entityId);

        String encodedString = Base64.getEncoder().encodeToString(attachmentDTO.getContent());

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.TEXT_PLAIN);
        respHeaders.setContentLength(encodedString.length());
        String FN = attachmentDTO.getName().replaceAll(",", ".");
        respHeaders.setContentDispositionFormData("attachment", FN);

//        InputStreamResource isr = new InputStreamResource(new ByteArrayInputStream(encodedString));
        return new ResponseEntity<String>(encodedString, HttpStatus.OK);
    }


    @GetMapping("/downloadAttachment")
    public ResponseEntity<InputStreamResource> downloadAttachment(@RequestParam(name = "entityId", required = true) Integer entityId, @RequestParam(name = "id", required = true) Integer id) {

        HttpHeaders headers = new HttpHeaders();
        ByteArrayInputStream bis = null;
        AttachmentDTO attachmentDTO = attachmentService.getAttachment(EntityType.PERSON, entityId, id);
//        MediaType type = MediaType.APPLICATION_OCTET_STREAM;  //APPLICATION_PROBLEM_JSON;

        try {
            bis = new ByteArrayInputStream(attachmentDTO.getContent());
            String FN = attachmentDTO.getName().replaceAll(",", ".");

            String headerView = "attachment; filename=" + FN;
            headers.add("Content-Disposition", headerView);
//            headers.add("Content-Description", "File Transfer");
        } catch (Exception e) {
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
