package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.HashTag;

import java.util.List;
import java.util.Optional;

public interface HashTagService {
    List<HashTag> findAll();

    List<HashTag> findLeafs(Integer id);

    Optional<HashTag> findById(Integer id);

    HashTag save(HashTag stock);

    void deleteById(Integer id);

    List<HashTag> search(String q);
}
