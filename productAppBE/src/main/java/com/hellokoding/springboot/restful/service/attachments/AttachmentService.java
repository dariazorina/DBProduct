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
    private IdSupplier idSupplier;

    /**
     * Create new attachment.
     * byte[] type for content is just for simplicity, maybe another type is needed.
     * This method should be thread-safe and can be called by many users in parallel.
     *
     * @param entityType parent entity type
     * @param entityId   parent entity id
     * @param fileName   original attachment filename
     * @param created    attachment creation time
     * @param content    attachment content
     * @return attachment id
     */
    public String createAttachment(EntityType entityType, Long entityId, String fileName, Instant created,
                                   byte[] content) {

        Path entityPath = Path.of(entityPathProvider.get(entityType, entityId));

        // when saving attachment to file system, keep in mind id may not be unique
        // check that file with such id does not exist yet
        // if it does- generate new id in loop
        // this operation may be not safe when multiple users save attachments
        String id = idSupplier.get();

        // generate attachment name for example as <attachment-id>-fileName
        // 234fg456-image.png
        // combine entity path with attachment name and save it to file system
        // set file creation date to created

        return id;
    }

    /**
     * Get attachment list.
     * This method should be thread-safe and can be called by many users in parallel.
     *
     * @param entityType parent entity type
     * @param entityId parent entity id
     * @return attachment list
     */
    public List<Attachment> getAttachments(EntityType entityType, Long entityId) {
        // build the entity folder path
        // read files in it
        // split file names into attachment id and attachment file name, read creation date
        // return attachment list with properties collected
        return null;
    }

    /**
     * Get attachment content.
     * This method should be thread-safe and can be called by many users in parallel.
     * byte[] type for content is just for simplicity, maybe another type is needed.
     *
     * @param entityType parent entity type
     * @param entityId parent entity id
     * @param id attachment id
     * @return attachment content
     */
    public byte[] getAttachment(EntityType entityType, Long entityId, String id) {
        // build attachment path and return content from file system
        return null;
    }

}
