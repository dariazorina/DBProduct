package com.hellokoding.springboot.restful.controller;

import com.hellokoding.springboot.restful.model.Project;
import com.hellokoding.springboot.restful.model.Status;
import com.hellokoding.springboot.restful.model.Tag;
import com.hellokoding.springboot.restful.model.dto.*;
import com.hellokoding.springboot.restful.service.ProjectService;
import com.hellokoding.springboot.restful.service.TagService;
import com.hellokoding.springboot.restful.service.attachments.AttachmentService;
import com.hellokoding.springboot.restful.service.dto.AttachmentDTO;
import com.hellokoding.springboot.restful.service.dto.EntityType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/project")
@Data
@Slf4j
public class ProjectAPI {

    private final ProjectService projectService;
    private final AttachmentService attachmentService;
    private final TagService tagService;


    @GetMapping("/allTags")
    public ResponseEntity<List<Tag>> findAll(@RequestParam(name = "q", required = false) String q) {
        if (!StringUtils.isEmpty(q)) {
            return ResponseEntity.ok(tagService.search(q));

        } else {
            return ResponseEntity.ok(tagService.findAll());
        }
    }


    @GetMapping
    public ResponseEntity<List<ProjectDtoForMainList>> findAll(@RequestParam(name = "mov", required = false) List<Integer> mov,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int size) {

        return ResponseEntity.ok(projectService.findAll(mov, page, size));
    }


    @GetMapping("/allQuant")
    public ResponseEntity<Integer> findQuantityAllProjects(@RequestParam(name = "mov", required = false) List<Integer> mov) {
        return ResponseEntity.ok(projectService.getQuantityAllProjectsWithMovement(mov));
    }


    @GetMapping("/search")
    public ResponseEntity<List<IdContentDto>> search(@RequestParam(name = "q", required = true) String q) {
        List<IdContentDto> search = projectService.search(q);
        return ResponseEntity.ok(search);
    }

    @PostMapping("/ids")
    public ResponseEntity<List<IdContentDto>> getProjectsByIds(@Valid @RequestBody List<Integer> idList) {
        return ResponseEntity.ok(projectService.findByIds(idList));
    }

    @PostMapping("/symmids/{id}")
    public ResponseEntity<List<OneTypeConnectionDto>> getProjectsByIdsAndSymmetrically(@PathVariable Integer id) { // @Valid @RequestBody List<Integer> idList) {
        return ResponseEntity.ok(projectService.findByIdsAndSymmetrically(id));
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ProjectDto project) {
        return ResponseEntity.ok(projectService.save(project));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> findById(@PathVariable Integer id) {
        Optional<ProjectDto> stock = projectService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable Integer id, @Valid @RequestBody ProjectDto project) {
        Optional<ProjectDto> stock = projectService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(projectService.save(project));
    }

    @PutMapping("/color/{id}")
    public ResponseEntity<Project> updateColor(@PathVariable Integer id, @Valid @RequestBody ProjectDtoForMainList project) {
        Optional<ProjectDto> stock = projectService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(projectService.saveColor(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<ProjectDto> stock = projectService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }
        projectService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/attachmentPhoto/{id}")
    public ResponseEntity deleteAttachmentPhoto(@PathVariable Integer id) {
        boolean res = attachmentService.deleteAttachmentPhoto(EntityType.PROJECT, id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/attachment")
    public ResponseEntity deleteAttachment(@RequestParam(name = "entityId", required = true) Integer entityId,
                                           @RequestParam(name = "fileId", required = true) Integer fileId,
                                           @RequestParam(name = "fileName", required = true) String fileName) {

        boolean res = attachmentService.deleteAttachment(EntityType.PROJECT, entityId, fileId, fileName);
        return ResponseEntity.ok(res);
    }


    @PostMapping("/attachment/{id}")
    public ResponseEntity<Integer> handleFileUpload(@PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        Integer attachmentId = attachmentService.createAttachment(EntityType.PROJECT, id, Instant.now(), "admin", file.getOriginalFilename(), file.getBytes());
//        if (attachmentId == 0) return ResponseEntity.ok(attachmentId); //todo?
        return ResponseEntity.ok(attachmentId);
    }


    @PostMapping("/attachmentPhoto/{id}")
    public ResponseEntity<Integer> handlePhotoUpload(@PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        Integer attachmentId = attachmentService.createPhotoAttachment(EntityType.PROJECT, id, Instant.now(), "admin", file.getOriginalFilename(), file.getBytes());
        return ResponseEntity.ok(attachmentId);
    }


    @GetMapping("/attachments/{id}")
    public ResponseEntity<List<AttachmentDTO>> getAttachments(@PathVariable Integer id) {
        List<AttachmentDTO> all = attachmentService.getAttachments(EntityType.PROJECT, id);
        return ResponseEntity.ok(all);
    }


    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    @GetMapping("/attachment")  ///for preview in blank window
    public ResponseEntity<InputStreamResource> getAttachment(@RequestParam(name = "entityId", required = true) Integer entityId, @RequestParam(name = "id", required = true) Integer id) {

        HttpHeaders headers = new HttpHeaders();
        ByteArrayInputStream bis = null;
        AttachmentDTO attachmentDTO = attachmentService.getAttachment(EntityType.PROJECT, entityId, id);
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

    @GetMapping("/attachmentPhoto/{entityId}")
    public ResponseEntity<String> getAttachmentPhoto(@PathVariable Integer entityId) {

        AttachmentDTO attachmentDTO = attachmentService.getAttachmentPhoto(EntityType.PROJECT, entityId);

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
        AttachmentDTO attachmentDTO = attachmentService.getAttachment(EntityType.PROJECT, entityId, id);
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
}
