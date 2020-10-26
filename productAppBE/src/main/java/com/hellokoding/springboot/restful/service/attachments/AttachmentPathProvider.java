package com.hellokoding.springboot.restful.service.attachments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
class AttachmentPathProvider {

    /**
     * Build the file system path to attachment.
     *
     * @param entityPath    entity home
     * @param id            attachment id
     * @param fileName      attachment filename
     * @return  attachment path
     */
    Path get(Path entityPath, Integer id, String fileName) {
        return Path.of(entityPath.toString(), String.format("%d#%s", id, fileName));
    }

}
