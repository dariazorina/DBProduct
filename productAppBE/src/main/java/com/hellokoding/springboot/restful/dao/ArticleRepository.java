package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {


    //search in title (title, titleRus simultaneously), "contains"
    @Query("from Article as a " +
            "where lower(a.title) like lower(:title) " +
            "or lower(a.titleRus) like lower(:title)")
    List<Article> findByTitle(String title);


    //search in hash, "starts with"
    @Query("select a " +
            "from Article a " +
            "join a.hashtagList h " +
            "where lower(h.content) like lower(:hashTag)")
//            "where h.content = :hashTag")
    List<Article> findByHash(String hashTag);


    //search in author surname only, "starts with"
    @Query("select a " +
            "from Article a " +
            "join a.authorList aL " +
            "where lower(aL.surname) like lower(:author)")
    List<Article> findByAuthor(String author);

}