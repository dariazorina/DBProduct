package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Annex;

import java.util.List;
import java.util.Optional;

public interface AnnexService {
    List<Annex> findAll();

    Optional<Annex> findById(Integer id);

    Annex save(Annex stock);

    void deleteById(Integer id);

    void fillAnnexTableFromEvent();

    void initializeReferenceBetweenAnnexAndEvent();
}
