package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {

    HashTag getHashTagByContent(String content);
    List<HashTag> findByContentStartsWithIgnoreCaseOrderByContentDesc (String content);
    List<HashTag> findByParentId(Integer id);
}
