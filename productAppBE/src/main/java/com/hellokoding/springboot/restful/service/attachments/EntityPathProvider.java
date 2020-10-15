package com.hellokoding.springboot.restful.service.attachments;

import com.hellokoding.springboot.restful.service.dto.EntityType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class EntityPathProvider {

    @Value("${attachments.root}")
    String attachmentsRoot;

    /**
     * Build the file system path to entity home.
     *
     * @param type  entity type
     * @param id    entity id
     * @return  entity home path
     */
    Path get(EntityType type, Long id) {
        return Path.of(attachmentsRoot, type.getName(), id.toString());
    }

}
