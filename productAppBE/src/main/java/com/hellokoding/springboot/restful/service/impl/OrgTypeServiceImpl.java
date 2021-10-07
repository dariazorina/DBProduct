package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.OrgType;
import com.hellokoding.springboot.restful.service.OrgTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrgTypeServiceImpl implements OrgTypeService {

    private final OrgTypeRepository orgTypeRepository;

    @Override
    public List<OrgType> findAll() {
        return orgTypeRepository.findAll();
    }

    @Override
    public Optional<OrgType> findById(Integer id) {
        return orgTypeRepository.findById(id);
    }

    @Override
    public List<OrgType> findLeafs(Integer id) {
        return orgTypeRepository.findByParentId(id);
    }

    @Override
    public List<OrgType> search(String q) {
        return orgTypeRepository.findByContentStartsWithIgnoreCaseOrderByContentDesc(q);
    }
}
