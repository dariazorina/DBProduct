package com.hellokoding.springboot.restful.service.attachments;

import com.hellokoding.springboot.restful.service.dto.AttachmentDTO;
import com.hellokoding.springboot.restful.service.dto.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

//import javax.sql.rowset.serial.SerialBlob;
//import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
//import java.sql.Blob;
//import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        Path entityPath = entityPathProvider.getPath(entityType, entityId);
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

    public Integer createPhotoAttachment(EntityType entityType, Integer entityId, Instant created, String user, String fileName, byte[] content) {

        Path entityPath = entityPathProvider.getPhotoPath(entityType, entityId);
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

    public boolean deleteAttachmentPhoto(EntityType entityType, Integer entityId) {

        Path entityPath = entityPathProvider.getPhotoPath(entityType, entityId);
        List<Path> pathFilesInFolder = new ArrayList<>();

        try {
            pathFilesInFolder = Files.walk(entityPath)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (pathFilesInFolder.size() == 1) {
            try {
                Files.delete(pathFilesInFolder.get(0));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
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

        Path entityPath = entityPathProvider.getPath(entityType, entityId);
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
//    public Blob getAttachment(EntityType entityType, Integer entityId, Integer id) {
//    public byte[] getAttachment(EntityType entityType, Integer entityId, Integer id) {
    public AttachmentDTO getAttachment(EntityType entityType, Integer entityId, Integer id) {
        // build attachment path and return content from file system

        AttachmentDTO attachmentDTO;
        BasicFileAttributes attributes;
        String stringsAfterSplit[];

        Path entityPath = entityPathProvider.getPath(entityType, entityId);
        List<Path> pathFilesInFolder = new ArrayList<>();

        try {
            pathFilesInFolder = Files.walk(entityPath)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        attachmentDTO = new AttachmentDTO();
        for (Path pathFile : pathFilesInFolder) {
            try {
                stringsAfterSplit = pathFile.getFileName().normalize().toString().split("#", 2);

                int i = Integer.parseInt(stringsAfterSplit[0]);
                if (i == id) {
                    attributes = Files.getFileAttributeView(pathFile, BasicFileAttributeView.class).readAttributes();
                    attachmentDTO.setDate(attributes.creationTime());
                    attachmentDTO.setSize(attributes.size());
                    attachmentDTO.setId(stringsAfterSplit[0]);
                    attachmentDTO.setName(stringsAfterSplit[1]);

//                    Blob blob = new SerialBlob(Files.readAllBytes(pathFile) );
//                    System.out.println(blob.length());
                    attachmentDTO.setContent(Files.readAllBytes(pathFile));
                    return attachmentDTO;

//                    return blob;
//                    return Files.readAllBytes(pathFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//            catch (SerialException e) {
//                e.printStackTrace();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
        }
        return null;
    }

    public AttachmentDTO getAttachmentPhoto(EntityType entityType, Integer entityId) {
        // build attachment path and return content from file system

        AttachmentDTO attachmentDTO;
        BasicFileAttributes attributes;
        String stringsAfterSplit[];

        Path entityPath = entityPathProvider.getPhotoPath(entityType, entityId);
        List<Path> pathFilesInFolder = new ArrayList<>();

        try {
            pathFilesInFolder = Files.walk(entityPath)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        attachmentDTO = new AttachmentDTO();
        for (Path pathFile : pathFilesInFolder) {
            try {
                stringsAfterSplit = pathFile.getFileName().normalize().toString().split("#", 2);

                attributes = Files.getFileAttributeView(pathFile, BasicFileAttributeView.class).readAttributes();
                attachmentDTO.setDate(attributes.creationTime());
                attachmentDTO.setSize(attributes.size());
                attachmentDTO.setId(stringsAfterSplit[0]);
                attachmentDTO.setName(stringsAfterSplit[1]);

//                Blob blob = new SerialBlob(Files.readAllBytes(pathFile));
//                System.out.println("----------------------------------------");
//                System.out.println(blob.length());

//                attachmentDTO.setBlobContent(blob);//Files.readAllBytes(pathFile));
                attachmentDTO.setContent(Files.readAllBytes(pathFile));

//                System.out.println("----------------------------------------");
//                System.out.println(attachmentDTO);

                return attachmentDTO;

            } catch (IOException e) {
                e.printStackTrace();
            } //catch (SerialException e) {
//                e.printStackTrace();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
        }
        return null;
    }
}
