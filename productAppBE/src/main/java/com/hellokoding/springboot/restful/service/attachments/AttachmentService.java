package com.hellokoding.springboot.restful.service.attachments;

import com.hellokoding.springboot.restful.service.dto.Attachment;
import com.hellokoding.springboot.restful.service.dto.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AttachmentService {

    private EntityPathProvider entityPathProvider;
    private AttachmentIdProvider idProvider;
    private AttachmentPathProvider pathProvider;

    /**
     * Create new attachment.
     * This method is thread-safe and can be used by multiple users concurrently without locks.
     * byte[] for content is just for simplicity, maybe another type is needed.
     *
     * @param entityType parent entity type
     * @param entityId   parent entity id
     * @param fileName   original attachment filename
     * @param created    attachment creation time
     * @param user       creator
     * @param content    attachment content
     * @return  attachment id
     */
    public Long createAttachment(EntityType entityType, Long entityId, Instant created, String user,
                                 String fileName, byte[] content) {

        Path entityPath = entityPathProvider.get(entityType, entityId);
        Long id = idProvider.get(entityType, entityId, created, user, fileName);
        Path attachmentPath = pathProvider.get(entityPath, id, fileName);

        // save content to attachmentPath
        // set new file creation date to created




//        Path path = ...
//        URI u = URI.create("http://java.sun.com/");
//        try (InputStream in = u.toURL().openStream()) {
//            Files.copy(in, path);
//        }


        return id;
    }

    /**
     * Get attachment list.
     * This method is thread-safe and can be used by multiple users concurrently without locks.
     *
     * @param entityType    parent entity type
     * @param entityId      parent entity id
     * @return  attachment list
     */
    public List<Attachment> getAttachments(EntityType entityType, Long entityId) {
        // build the entity folder path
        // read files in it
        // split file names into attachment id and attachment file name, get file creation date
        // return attachment list with properties collected
        // for now we use file system as the only "source of truth" for entity attachments
        return null;
    }

    /**
     * Get attachment content.
     * This method is thread-safe and can be used by multiple users concurrently without locks.
     * byte[] for content is just for simplicity, maybe another type is needed.
     *
     * @param entityType    parent entity type
     * @param entityId      parent entity id
     * @param id            attachment id
     * @return  attachment content
     */
    public byte[] getAttachment(EntityType entityType, Long entityId, Long id) {
        // build attachment path and return content from file system
        return null;
    }

}
