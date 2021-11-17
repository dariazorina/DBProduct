package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.model.dto.ArticleDto;
import com.hellokoding.springboot.restful.model.dto.ArticleDtoForMainList;
import com.hellokoding.springboot.restful.model.dto.IdContentDto;
import com.hellokoding.springboot.restful.model.dto.NameConnectionDto;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<ArticleDtoForMainList> filter(List<String> title, List<String> hash, List<String> author, List<String> org,
                            List<String> location, List<String> lang, String descr, String text, List<String> misc,
                            List<Integer> status, String startDate, String endDate, List<Integer> movement);

    List<ArticleDtoForMainList> search(String descr, String text, List<Integer> status, String startDate, String endDate, List<Integer> movement);

    List<IdContentDto> searchMaterial(String q);
    List<IdContentDto> searchMaterialById(Integer id);

//    List<ArticleDto> findAll();

    Optional<ArticleDto> findById(Integer id);

    Article save(ArticleDto stock);
    Article saveColor (ArticleDtoForMainList articleDto);

    void deleteById(Integer id);

    List<IdContentDto> findByIds(List<Integer> idList);

    List<NameConnectionDto> findByIdsAndSymmetrically(Integer id);
    }
