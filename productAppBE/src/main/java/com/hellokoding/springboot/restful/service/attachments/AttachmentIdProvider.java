package com.hellokoding.springboot.restful.service.attachments;

import com.hellokoding.springboot.restful.dao.AttachmentRepository;
import com.hellokoding.springboot.restful.model.Attachment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AttachmentIdProvider {
    private final AttachmentRepository attachmentRepository;
    Integer get() {
        Attachment attachment = new Attachment();
        Attachment savedAttachment = attachmentRepository.save(attachment);
        return savedAttachment.getId();
    }
}
