package com.hellokoding.springboot.restful.service.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class Attachment {

    private String id;
    private String fileName;
    private Instant created;

}
