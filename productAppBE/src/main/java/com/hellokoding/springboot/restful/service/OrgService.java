package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Org;
import com.hellokoding.springboot.restful.model.dto.IdContentDto;
import com.hellokoding.springboot.restful.model.dto.NameConnectionDto;
import com.hellokoding.springboot.restful.model.dto.OrgDto;

import java.util.List;
import java.util.Optional;

public interface OrgService {
    List<Org> findAll();
    List<OrgDto> findAll(Integer mov);
    Optional<OrgDto> findById(Integer id);
    Org save(OrgDto stock);
    void deleteById(Integer id);
    List<IdContentDto> search(String q);
    List<IdContentDto> findByIds(List<Integer> idList);
    List<NameConnectionDto> findByIdsAndSymmetrically(Integer id);
}
