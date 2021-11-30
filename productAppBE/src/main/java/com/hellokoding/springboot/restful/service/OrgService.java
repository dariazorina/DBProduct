package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Org;
import com.hellokoding.springboot.restful.model.dto.IdContentDto;
import com.hellokoding.springboot.restful.model.dto.NameConnectionDto;
import com.hellokoding.springboot.restful.model.dto.OrgDto;
import com.hellokoding.springboot.restful.model.dto.OrgDtoForMainList;

import java.util.List;
import java.util.Optional;

public interface OrgService {
    List<Org> findAll();
   // List<OrgDto> findAll(Integer mov);
    List<OrgDtoForMainList> findAll(List<Integer> mov);
    Optional<OrgDto> findById(Integer id);
    Org save(OrgDto stock);
    Org saveColor(OrgDtoForMainList stock);
    void deleteById(Integer id);
    List<IdContentDto> search(String q);
    List<IdContentDto> findByIds(List<Integer> idList);
    List<OrgDtoForMainList> filter(List<String> hash, List<String> name, List<String> location, List<Integer> mov);
    List<NameConnectionDto> findByIdsAndSymmetrically(Integer id);
}
