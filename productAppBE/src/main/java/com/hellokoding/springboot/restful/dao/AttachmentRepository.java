package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}
