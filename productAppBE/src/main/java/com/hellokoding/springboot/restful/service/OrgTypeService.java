package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.OrgType;

import java.util.List;
import java.util.Optional;

public interface OrgTypeService {
    List<OrgType> findAll();
    List<OrgType> findLeafs(Integer id);
    Optional<OrgType> findById(Integer id);
    List<OrgType> search(String q);
}
