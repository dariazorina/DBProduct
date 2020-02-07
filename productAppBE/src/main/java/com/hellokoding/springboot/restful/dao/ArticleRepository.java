package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
//    List<Article> findByTitleAndTitleRusContains(String title);
    List<Article> findByTitleStartsWithIgnoreCase(String title);

    List<Article> findByTitleAndTitleRusStartsWith(String title, String title1);
//    List<Article> findByHashtagListStartsWithIgnoreCase(String hash);


//    List<Person> findBySurnameStartsWithIgnoreCase(String surname);


}