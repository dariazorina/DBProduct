package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {


    //search in title (title, titleRus simultaneously), "contains"
    @Query("from Article as a " +
            "where (lower(a.title) like lower(:title) " +
            "or lower(a.titleRus) like lower(:title)) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByTitleAndDate(String title, Date startDate, Date endDate);


    //search in hash, "starts with"
    @Query("select a " +
            "from Article a " +
            "join a.hashtagList h " +
            "where lower(h.content) like lower(:hashTag) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
//            "where h.content = :hashTag")
    List<Article> findByHash(String hashTag, Date startDate, Date endDate);


    //search in author surname and surnameRus, "starts with"
    @Query("select a " +
            "from Article a " +
            "join a.authorList aL " +
            "where lower(aL.surname) like lower(:author) " +
            "or lower(aL.surnameRus) like lower(:author) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByAuthor(String author, Date startDate, Date endDate);


    //search status+date
    @Query("from Article a " +
            "where a.status = :status and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByDateAndStatus(Integer status, Date startDate, Date endDate);


    //search status+author
    @Query("select a " +
            "from Article a " +
            "join a.authorList aL " +
            "where (lower(aL.surname) like lower(:author) " +
            "or lower(aL.surnameRus) like lower(:author)) " +
            "and a.status = :status " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByAuthorAndStatus(String author, Integer status, Date startDate, Date endDate);

    //search status+hash
    @Query("select a " +
            "from Article a " +
            "join a.hashtagList h " +
            "where lower(h.content) like lower(:hashTag) " +
            "and a.status = :status " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByHashAndStatus(String hashTag, Integer status, Date startDate, Date endDate);

    //search status+title
    @Query("from Article a " +
            "where (lower(a.title) like lower(:title) or lower(a.titleRus) like lower(:title)) " +
            "and a.status = :status " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByTitleAndStatusAndDate(@Param("title") String title, @Param("status") Integer status, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


    //search date range
    List<Article> findAllByDateBetween(Date startDate, Date endDate);
}