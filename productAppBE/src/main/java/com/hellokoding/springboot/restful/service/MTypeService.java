package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.HashTag;
import com.hellokoding.springboot.restful.model.MaterialType;
import javafx.scene.transform.MatrixType;

import java.util.List;
import java.util.Optional;

public interface MTypeService {
    List<MaterialType> findAll();

    List<MaterialType> findLeafs(Integer id);

    Optional<MaterialType> findById(Integer id);

    MaterialType save(MaterialType stock);

    void deleteById(Integer id);

    List<MaterialType> search(String q);
}
