package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.UrlLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlLinkRepository extends JpaRepository<UrlLink, Integer> {
    UrlLink getUrlLinkByContent(String content);
}
