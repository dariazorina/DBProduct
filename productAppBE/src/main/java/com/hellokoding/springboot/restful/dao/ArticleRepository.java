package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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


    //search in author surname and surnameRus, "starts with"
    @Query("select a " +
            "from Article a " +
            "join a.authorList aL " +
            "where lower(aL.surname) like lower(:author) " +
            "or lower(aL.surnameRus) like lower(:author)")
    List<Article> findByAuthor(String author);


    //search 0/1 status
    List<Article> findByStatus(Integer status);


    //search status+author
    @Query("select a " +
            "from Article a " +
            "join a.authorList aL " +
            "where (lower(aL.surname) like lower(:author) " +
            "or lower(aL.surnameRus) like lower(:author)) " +
            "and a.status = :status")
    List<Article> findByAuthorAndStatus(String author, Integer status);

    //search status+hash
    @Query("select a from Article a join a.hashtagList h where lower(h.content) like lower(:hashTag) and a.status = :status")
    List<Article> findByHashAndStatus(String hashTag, Integer status);

    //search status+title
    @Query( "from Article a " +
            "where (lower(a.title) like lower(:title) or lower(a.titleRus) like lower(:title)) and a.status = :status")
    List<Article> findByTitleAndStatus(@Param("title") String title, @Param("status") Integer status);
}