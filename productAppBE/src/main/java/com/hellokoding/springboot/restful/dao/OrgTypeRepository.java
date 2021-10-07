package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.OrgType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrgTypeRepository extends JpaRepository<OrgType, Integer> {

    OrgType getTypeByContent(String content);
    List<OrgType> findByParentId(Integer id);
    List<OrgType> findByContentStartsWithIgnoreCaseOrderByContentDesc(String content);
}
