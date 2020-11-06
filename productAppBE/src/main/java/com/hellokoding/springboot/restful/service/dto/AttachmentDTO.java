package com.hellokoding.springboot.restful.service.dto;

import lombok.Data;
import java.nio.file.attribute.FileTime;

@Data
public class AttachmentDTO {

    private String id;
    private String name;
    private FileTime date;
    private Long size;
    //author? todo
    private byte[] content;
}
