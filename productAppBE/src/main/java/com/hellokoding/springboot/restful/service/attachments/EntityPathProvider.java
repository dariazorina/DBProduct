package com.hellokoding.springboot.restful.service.attachments;

import com.hellokoding.springboot.restful.service.dto.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
class EntityPathProvider {

    @Value("${attachments.root}")
    private String attachmentsRoot;
    /**
     * Build the file system path to entity home.
     *
     * @param type  entity type
     * @param id    entity id
     * @return  entity home path
     */
    Path get(EntityType type, Integer id) {
        return Path.of(attachmentsRoot, type.getName(), id.toString());
    }
}
