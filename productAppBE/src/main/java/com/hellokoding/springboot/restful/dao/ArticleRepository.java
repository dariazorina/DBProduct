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

    //search in description, "contains"
    @Query("from Article as a " +
            "where lower(a.description) like lower(:description) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByDescriptionAndDate(String description, Date startDate, Date endDate);


    //search in language, "starts with"
    @Query("select a from Article as a join a.language l where lower(l.name) like lower(:lang) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByLangAndDate(String lang, Date startDate, Date endDate);


//    //search in hash, "starts with"
//    @Query("select a " +
//            "from Article a " +
//            "join a.hashtagList h " +
//            "where lower(h.content) like lower(:hashTag) " +
//            "and (a.date >=:startDate and a.date <= :endDate)")
////            "where h.content = :hashTag")
//    List<Article> findByHashAndDate(String hashTag, Date startDate, Date endDate);


    //search in author surname and surnameRus, "starts with"
    @Query("select a " +
            "from Article a " +
            "join a.authorList aL " +
            "where lower(aL.surname) like lower(:author) " +
            "or lower(aL.surnameRus) like lower(:author) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByAuthorAndDate(String author, Date startDate, Date endDate);

    ///////////////////////////////////////////////////////////////////


    //search status+date
    @Query("from Article a " +
            "where a.status in (:status) and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByDateAndStatus(List<Integer> status, Date startDate, Date endDate);


    //////////////////////////////////////////////////////////////////////

    //search status+author
    @Query("select a " +
            "from Article a " +
            "join a.authorList aL " +
            "where (lower(aL.surname) like lower(:author) " +
            "or lower(aL.surnameRus) like lower(:author)) " +
            "and a.status = :status " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByAuthorAndStatusAndDate(String author, List<Integer> status, Date startDate, Date endDate);
    //search status+author
    @Query("select a " +
            "from Article a " +
            "join a.authorList aL " +
            "where (lower(aL.surname) like lower(:author) " +
            "or lower(aL.surnameRus) like lower(:author)) " +
            "and a.status = :status ")
    List<Article> findByAuthorAndStatus(String author, List<Integer> status);



//    //search status+hash
//    @Query("select a " +
//            "from Article a " +
//            "join a.hashtagList h " +
//            "where lower(h.content) like lower(:hashTag) " +
//            "and a.status in (:status) " +
//            "and (a.date >=:startDate and a.date <= :endDate)")
//    List<Article> findByHashAndStatusAndDate(String hashTag, List<Integer> status, Date startDate, Date endDate);
//    //search status+hash
//    @Query("select a " +
//            "from Article a " +
//            "join a.hashtagList h " +
//            "where lower(h.content) like lower(:hashTag) " +
//            "and a.status in (:status)")
//    List<Article> findByHashAndStatus(String hashTag, List<Integer> status);



    //search status+title
    @Query("from Article a " +
            "where (lower(a.title) like lower(:title) or lower(a.titleRus) like lower(:title)) " +
            "and a.status in (:status) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByTitleAndStatusAndDate(@Param("title") String title, @Param("status") List<Integer> status, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    //search status+title
    @Query("from Article a " +
            "where (lower(a.title) like lower(:title) or lower(a.titleRus) like lower(:title)) " +
            "and a.status in (:status)")
    List<Article> findByTitleAndStatus(@Param("title") String title, @Param("status") List<Integer> status);



    //search status+description
    @Query("from Article a " +
            "where lower(a.description) like lower(:description) " +
            "and a.status in (:status) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByDescriptionAndStatusAndDate(String description, List<Integer> status, Date startDate, Date endDate);
    //search status+description
    @Query("from Article a " +
            "where lower(a.description) like lower(:description) " +
            "and a.status in (:status) ")
    List<Article> findByDescriptionAndStatus(String description, List<Integer> status);


    //search in language+status, "starts with"
    @Query("from Article a " +
            "where lower(a.language.name) like lower(:lang) " +
            "and a.status in (:status) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByLangAndStatusAndDate(String lang, List<Integer> status, Date startDate, Date endDate);
    //search in language+status, "starts with"
    @Query("from Article a " +
            "where lower(a.language.name) like lower(:lang) " +
            "and a.status in (:status) ")
    List<Article> findByLangAndStatus(String lang, List<Integer> status);



    //search date range
    List<Article> findAllByDateBetween(Date startDate, Date endDate);
    //search by status
    List<Article> findAllByStatus(List<Integer>status);

}