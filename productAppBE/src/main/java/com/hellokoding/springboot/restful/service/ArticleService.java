package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.model.dto.ArticleDto;
import com.hellokoding.springboot.restful.model.dto.IdContentDto;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<ArticleDto> search(List<String> title, List<String> hash, List<String> author, List<String> org,
                            List<String> location, List<String> lang, String descr, String text, List<String> misc,
                            List<Integer> status, String startDate, String endDate);
    List<IdContentDto> searchMaterial(String q);
    List<IdContentDto> searchMaterialById(Integer id);
    List<ArticleDto> findAll();
    Optional<ArticleDto> findById(Integer id);
    Article save(ArticleDto stock);
    void deleteById(Integer id);
    List<Article> findByIds(List<Integer> idList);
}
