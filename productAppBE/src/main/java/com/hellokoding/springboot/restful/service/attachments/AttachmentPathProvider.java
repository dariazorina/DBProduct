package com.hellokoding.springboot.restful.service.attachments;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class AttachmentPathProvider {

    /**
     * Build the file system path to attachment.
     *
     * @param entityPath    entity home
     * @param id            attachment id
     * @param fileName      attachment filename
     * @return  attachment path
     */
    Path get(Path entityPath, Long id, String fileName) {
        return Path.of(entityPath.toString(), String.format("%d-%s", id, fileName));
    }

}
