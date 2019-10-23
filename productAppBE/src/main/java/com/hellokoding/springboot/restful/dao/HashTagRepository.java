package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {
    HashTag getHashTagByContent(String content);
}
