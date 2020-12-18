package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MTypeRepository extends JpaRepository<MaterialType, Integer> {

    MaterialType getMaterialTypeByContent(String content);
    List<MaterialType> findByContentStartsWithIgnoreCaseOrderByContentDesc(String content);
    List<MaterialType> findByParentId(Integer id);
}
