package com.hellokoding.springboot.restful.service.attachments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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
