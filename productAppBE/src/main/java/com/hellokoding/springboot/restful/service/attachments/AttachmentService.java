package com.hellokoding.springboot.restful.service.attachments;

import com.hellokoding.springboot.restful.service.dto.AttachmentDTO;
import com.hellokoding.springboot.restful.service.dto.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@Service
@Component
@RequiredArgsConstructor
public class AttachmentService {

    final private EntityPathProvider entityPathProvider;
    final private AttachmentIdProvider idProvider;
    final private AttachmentPathProvider pathProvider;

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
     * @return attachment id
     */
    public Integer createAttachment(EntityType entityType, Integer entityId, Instant created, String user, String fileName, byte[] content) {

        Path entityPath = entityPathProvider.get(entityType, entityId);
        Integer id = idProvider.get();
        Path attachmentPathAndName = pathProvider.get(entityPath, id, fileName);

        try {
            Files.createDirectories(attachmentPathAndName.getParent());
            System.out.println(attachmentPathAndName.toAbsolutePath());
            Files.write(attachmentPathAndName, content, StandardOpenOption.APPEND, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Get attachment list.
     * This method is thread-safe and can be used by multiple users concurrently without locks.
     *
     * @param entityType parent entity type
     * @param entityId   parent entity id
     * @return attachment list
     */
    public List<AttachmentDTO> getAttachments(EntityType entityType, Integer entityId) {
        // build the entity folder path  // read files in it
        // split file names into attachment id and attachment file name, get file creation date
        // return attachment list with properties collected
        // for now we use file system as the only "source of truth" for entity attachments

        Path entityPath = entityPathProvider.get(entityType, entityId);
        List<Path> pathFilesInFolder = new ArrayList<>();

        try {
            pathFilesInFolder = Files.walk(entityPath)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        AttachmentDTO attachmentDTO;
        BasicFileAttributes attributes;
        String stringsAfterSplit[];
        List<AttachmentDTO> attachments = new ArrayList<>();

        for (Path pathFile : pathFilesInFolder) {
            attachmentDTO = new AttachmentDTO();

            try {
                attributes = Files.getFileAttributeView(pathFile, BasicFileAttributeView.class).readAttributes();
                attachmentDTO.setDate(attributes.creationTime());
                attachmentDTO.setSize(attributes.size());

                stringsAfterSplit = pathFile.getFileName().normalize().toString().split("#", 2);

                attachmentDTO.setId(stringsAfterSplit[0]);
                attachmentDTO.setName(stringsAfterSplit[1]);

                attachments.add(attachmentDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return attachments;
    }

    /**
     * Get attachment content.
     * This method is thread-safe and can be used by multiple users concurrently without locks.
     * byte[] for content is just for simplicity, maybe another type is needed.
     *
     * @param entityType parent entity type
     * @param entityId   parent entity id
     * @param id         attachment id
     * @return attachment content
     */
    public byte[] getAttachment(EntityType entityType, Integer entityId, Integer id) {
        // build attachment path and return content from file system
        return null;
    }

}
