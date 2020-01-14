package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Isource;

import java.util.List;
import java.util.Optional;

public interface IsourceService {
    List<Isource> findAll();

    Optional<Isource> findById(Integer id);

    Isource save(Isource stock);

    void deleteById(Integer id);
}
