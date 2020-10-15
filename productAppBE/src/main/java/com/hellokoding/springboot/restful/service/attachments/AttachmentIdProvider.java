package com.hellokoding.springboot.restful.service.attachments;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class AttachmentIdProvider {

    /**
     * Get attachment id unique in scope of system.
     *
     * @return  unique attachment id
     */
    Long get() {
        // create new table and entity "attachment" with autoincremented primary-key id
        return 0L;
    }

}
