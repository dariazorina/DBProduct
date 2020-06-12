package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Org;

import java.util.List;
import java.util.Optional;

public interface OrgService {
    List<Org> findAll();

    Optional<Org> findById(Integer id);

    Org save(Org stock);

    void deleteById(Integer id);
}
