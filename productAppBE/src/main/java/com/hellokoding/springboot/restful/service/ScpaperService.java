package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Scpaper;

import java.util.List;
import java.util.Optional;

public interface ScpaperService {
    List<Scpaper> findAll();

    Optional<Scpaper> findById(Integer id);

    Scpaper save(Scpaper stock);

    void deleteById(Integer id);
}
