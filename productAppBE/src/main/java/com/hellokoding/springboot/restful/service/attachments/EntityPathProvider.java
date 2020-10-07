package com.hellokoding.springboot.restful.service.attachments;

import com.hellokoding.springboot.restful.service.dto.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class EntityPathProvider {

    @Value("${attachments.root}")
    private String attachmentsRoot;

    public String get(EntityType type, Long id) {
        return Path.of(attachmentsRoot, type.toString(), id.toString()).toString();
    }

}
