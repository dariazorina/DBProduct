package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.MaterialType;
import com.hellokoding.springboot.restful.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> getTagByContent(String content);
}
