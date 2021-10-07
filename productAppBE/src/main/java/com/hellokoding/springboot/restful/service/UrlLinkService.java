package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.UrlLink;

import java.util.List;
import java.util.Optional;

public interface UrlLinkService {
    List<UrlLink> findAll();

    Optional<UrlLink> findById(Integer id);

    UrlLink save(UrlLink stock);

    void deleteById(Integer id);

    public List<UrlLink> getLinkListID(List<UrlLink> linkList);
}
