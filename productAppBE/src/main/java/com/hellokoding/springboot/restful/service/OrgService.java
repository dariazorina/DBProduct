package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Org;
import com.hellokoding.springboot.restful.model.dto.*;

import java.util.List;
import java.util.Optional;

public interface OrgService {
//    List<Org> findAll();
//    Integer getQuantityAllOrgsWithMovement(List<Integer> mov);
//    List<OrgDtoForMainList> findAll(List<Integer> mov, Integer page, Integer size);
//    Optional<OrgDto> findById(Integer id);

    Optional<OrgDto> findById(Integer id);
    Org save(OrgDto stock);
    Org saveColor(OrgDtoForMainList stock);
    void deleteById(Integer id);
    List<IdContentDto> search(String q);
    List<IdContentDto> findByIds(List<Integer> idList);
    PagedDataDto filter(List<String> hash, List<String> name, List<String> location, List<String> org, List<Integer> mov, Integer page, Integer size);
    List<OneTypeConnectionDto> findByIdsAndSymmetrically(Integer id);
}
