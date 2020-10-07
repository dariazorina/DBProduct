package com.hellokoding.springboot.restful.service.attachments;

import com.hellokoding.springboot.restful.service.dto.EntityType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class AttachmentIdProvider {

    /**
     * Get attachment id unique in scope of system.
     *
     * @param entityType parent entity type
     * @param entityId   parent entity id
     * @param created    creation time
     * @param user       creator
     * @return  unique attachment id
     */
    Long get(EntityType entityType, Long entityId, Instant created, String user, String fileName) {
        // create new table and entity "attachment" with autoincremented id and fields in parameters
        // (as usual, in model, not here of course)
        // all fields are not null
        // for now we will just use this entity to generate unique numeric ids for attachments,
        // and act as a "log" of attachment creation
        // no unique constraints are needed in this table, only primary key for id,
        // and just when we need attachment id- we create a new entity
        // (here from this provider)
        // and return the new id
        // Postgres guarantees us new id uniqueness
        return 0L;
    }

}
