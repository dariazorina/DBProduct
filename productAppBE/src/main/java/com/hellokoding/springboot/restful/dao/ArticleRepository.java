package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Integer> {


//    //search in title (title, titleRus simultaneously), "contains" +date
//    @Query("from Article as a " +
//            "join a.language l join a.hashtagList h " +
//            "join h.assigned_hashtag assh " +
//            "where h.assigned_hashtag = assh.id " +
//            "and (:hashTag is null or assh.content like :hashTag)" +
//            "and (:lang is null or l.name like :lang)" +
//            "and (lower(a.title) like lower(:title) " +
//            "or lower(a.titleRus) like lower(:title)) " +
//
//            "and (a.date >=:startDate and a.date <= :endDate)")
//    Set<Article> findByFiltersAndDate(String title, String hashTag, String lang, Date startDate, Date endDate);


    //works if all three fileds not null
//    //search in title (title, titleRus simultaneously), "contains" +date
//    @Query("from Article as a " +
//            "join a.language l join a.hashtagList h " +
//            "join h.assigned_hashtag assh " +
//            "where a.hashtagList is EMPTY or assh is null or h.assigned_hashtag = assh.id " +
//            "and (:hashTag is null or assh.content like :hashTag)" +
//            "and (:lang is null or l.name like :lang)" +
//            "and (lower(a.title) like lower(:title) " +
//            "or lower(a.titleRus) like lower(:title)) " +
//
//            "and (a.date >=:startDate and a.date <= :endDate)")
//    Set<Article> findByFiltersAndDate(String title, String hashTag, String lang, Date startDate, Date endDate);


//    //search in title (title, titleRus simultaneously), "contains" +date
//    @Query("from Article as a " +
//            "join a.hashtagList h " +
//            "join h.assigned_hashtag assh " +
//            "where ((a.hashtagList is empty or " +
//            "(:hashTag is null or assh.content like :hashTag))" +
//            "and (lower(a.title) like lower(:title) " +
//            "or lower(a.titleRus) like lower(:title)) " +
//            "and (a.date >=:startDate and a.date <= :endDate))")
//    Set<Article> findByFiltersAndDate(String title, String hashTag, Date startDate, Date endDate);

//    //search in title (title, titleRus simultaneously), "contains" +date
//    @Query("from Article as a " +
//            "join a.language l " +
//            "left join a.hashtagList h " +
//            "left join h.assigned_hashtag assh " +
//            "where ((a.hashtagList is empty or " +
//            ":hashTag is null or assh.content like :hashTag)" +
//            "and (:comment is null or a.miscellany like :comment)" +
//            "and (:lang is null or lower(cast (:lang as string)) like lower(l.name))" +
//            "and (lower(a.title) like lower(:title) " +
//            "or lower(a.titleRus) like lower(:title)) " +
//            "and (a.date >=:startDate and a.date <= :endDate))")
//    Set<Article> findByFiltersAndDate(String title, String hashTag,  String lang, String comment, Date startDate, Date endDate);
//


//    // working filter
//    @Query("select distinct a " +
//            "from Article as a " +
//            "left join a.hashtagList h " +
//            "left join h.assigned_hashtag assh " +
//            "join a.language l " +
//            "where ((:hashTag is null or assh.content like :hashTag)" +
//            "and (:comment is null or lower(a.miscellany) like :comment)" +
//            "and (:lang is null or lower(l.name) like :lang)" +
//            "and (:title is null or lower(a.titleRus) like :title) " +
//            "and (a.date >=:startDate and a.date <= :endDate))")
//    Set<Article> findByFiltersAndDate(String title, String hashTag,  String lang, String comment, Date startDate, Date endDate);


    ///////////////FILTER/////////////////
    @Query("select distinct a " +
            "from Article as a " +
            "left join a.hashtagList h " +
            "left join h.assigned_hashtag assh " +
            "left join a.personConnections aL " +
            "left join aL.person aP " +
            "left join a.orgConnections aO " +
            "left join aO.org aOrg " +
            "left join a.locationConnections aLo " +
            "left join aLo.location aLoc " +
            "join a.language l " +
            "where ((:org is null  or lower(aOrg.nameRus) like :org) " +
            "and (:location is null or lower(aLoc.country) like :location) " +
            "and (:author is null or lower(aP.surnameRus) like :author) " +
            "and (:hashTag is null or assh.content like :hashTag) " +
            "and (:comment is null or lower(a.miscellany) like :comment) " +
            "and (:text is null or lower(a.text) like :text) " +
            "and (:description is null or lower(a.description) like :description) " +
            "and (:lang is null or lower(l.name) like :lang) " +
            "and (:title is null or lower(a.titleRus) like :title) " +
            "and (a.date >=:startDate and a.date <= :endDate))")
    Set<Article> findByFiltersAndDate(String title, String hashTag, String author, String org, String location,
                                      String lang, String comment, String text, String description, Date startDate, Date endDate);

    @Query("select distinct a " +
            "from Article as a " +
            "left join a.hashtagList h " +
            "left join h.assigned_hashtag assh " +
            "left join a.personConnections aL " +
            "left join aL.person aP " +
            "left join a.orgConnections aO " +
            "left join aO.org aOrg " +
            "left join a.locationConnections aLo " +
            "left join aLo.location aLoc " +
            "join a.language l " +
            "where ((:org is null  or lower(aOrg.nameRus) like :org) " +
            "and (:location is null or lower(aLoc.country) like :location) " +
            "and (:author is null or lower(aP.surnameRus) like :author) " +
            "and (:hashTag is null or assh.content like :hashTag) " +
            "and (:comment is null or lower(a.miscellany) like :comment) " +
            "and (:text is null or lower(a.text) like :text) " +
            "and (:description is null or lower(a.description) like :description) " +
            "and (:lang is null or lower(l.name) like :lang) " +
            "and (:title is null or lower(a.titleRus) like :title) " +
            "and a.status in (:status) " +
            "and (a.date >=:startDate and a.date <= :endDate))")
    Set<Article> findByFiltersAndDateAndStatus(String title, String hashTag, String author, String org, String location,
                                               String lang, String comment, String text, String description, List<Integer> status, Date startDate, Date endDate);


    //////////////MATERIALS///////////
    @Query("from Article as a " +
            "where (lower(a.title) like lower(:title) " +
            "or lower(a.titleRus) like lower(:title))")
    List<Article> findMaterialByTitle(String title);


    ////////////TITLE///////////////
    //search in title (title, titleRus simultaneously), "contains" +date
    @Query("from Article as a " +
            "where (lower(a.title) like lower(:title) " +
            "or lower(a.titleRus) like lower(:title)) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByTitleAndDate(String title, Date startDate, Date endDate);

    //    //search status+title+date
    @Query("from Article a " +
            "where (lower(a.title) like lower(:title) or lower(a.titleRus) like lower(:title)) " +
            "and a.status in (:status) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByTitleAndStatusAndDate(@Param("title") String title, @Param("status") List<Integer> status, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


    /////////////////////DESCR///////////////////////
    //search in description, "contains" +date
    @Query("from Article as a " +
            "where lower(a.text) like lower(:text) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    Set<Article> findByTextAndDate(String text, Date startDate, Date endDate);

    //search status+description+date
    @Query("from Article a " +
            "where lower(a.text) like lower(:text) " +
            "and a.status in (:status) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    Set<Article> findByTextAndStatusAndDate(String text, List<Integer> status, Date startDate, Date endDate);


    /////////////////////DESCR///////////////////////
    //search in description, "contains" +date
    @Query("from Article as a " +
            "where lower(a.description) like lower(:description) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    Set<Article> findByDescriptionAndDate(String description, Date startDate, Date endDate);

    //search status+description+date
    @Query("from Article a " +
            "where lower(a.description) like lower(:description) " +
            "and a.status in (:status) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    Set<Article> findByDescriptionAndStatusAndDate(String description, List<Integer> status, Date startDate, Date endDate);


    /////////////////////////LANG//////////////
//    //search in language, "starts with"
    @Query("select a from Article as a join a.language l where lower(l.name) like lower(:lang) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByLangAndDate(String lang, Date startDate, Date endDate);

    //    //search in language+status, "starts with"
    @Query("from Article a " +
            "where lower(a.language.name) like lower(:lang) " +
            "and a.status in (:status) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByLangAndStatusAndDate(String lang, List<Integer> status, Date startDate, Date endDate);


    ///////////HASH/////////////
//    //search in hash, "starts with" +date
    @Query("select distinct a " +   //to remove duplicate if item has several hashtags start with search word
            "from Article a " +
            "join a.hashtagList h " +
            "join h.assigned_hashtag assh " +
            "where h.assigned_hashtag = assh.id " +
            "and lower(assh.content) like lower(:hashTag)" +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByHashAndDate(String hashTag, Date startDate, Date endDate);

    //    //search status+hash+date
    @Query("select distinct a " +   //to remove duplicate if item has several hashtags start with search word
            "from Article a " +
            "join a.hashtagList h " +
            "join h.assigned_hashtag assh " +
            "where h.assigned_hashtag = assh.id " +
            "and lower(assh.content) like lower(:hashTag)" +
            "and a.status in (:status) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByHashAndStatusAndDate(String hashTag, List<Integer> status, Date startDate, Date endDate);


    ////////////AUTHOR//////////
//    //search in author surname and surnameRus, "starts with" +date
    @Query("select distinct a " +
            "from Article a " +
            "join a.personConnections aL " +
            "join aL.person aP " +
            "where (lower(aP.surname) like lower(:author) " +
            "or lower(aP.surnameRus) like lower(:author) ) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByAuthorAndDate(String author, Date startDate, Date endDate);

    //    //search status+author+date
    @Query("select distinct a " +
            "from Article a " +
            "join a.personConnections aL " +
            "join aL.person aP " +
            "where (lower(aP.surname) like lower(:author) " +
            "or lower(aP.surnameRus) like lower(:author)) " +
            "and a.status in :status " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByAuthorAndStatusAndDate(String author, List<Integer> status, Date startDate, Date endDate);

    ////////////MISC////////////////
    //search status+misc+date
    @Query("from Article a " +
            "where lower(a.miscellany) like lower(:miscellany) " +
            "and a.status in (:status) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByMiscellanyAndStatusAndDate(String miscellany, List<Integer> status, Date startDate, Date endDate);

    //search in miscellany, "contains" +date
    @Query("from Article as a " +
            "where lower(a.miscellany) like lower(:miscellany) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByMiscellanyAndDate(String miscellany, Date startDate, Date endDate);


    /////////////ORG/////////////
//    //search status+org+date
    @Query("select distinct a " +
            "from Article a " +
            "join a.orgConnections aL " +
            "join aL.org aP " +
            "where (lower(aP.name) like lower(:org) " +
            "or lower(aP.nameRus) like lower(:org)) " +
            "and a.status in :status " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByOrgAndStatusAndDate(String org, List<Integer> status, Date startDate, Date endDate);

    //    //search in org, "contains" +date
    @Query("select distinct a " +
            "from Article a " +
            "join a.orgConnections aL " +
            "join aL.org aP " +
            "where (lower(aP.name) like lower(:org) " +
            "or lower(aP.nameRus) like lower(:org) ) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByOrgAndDate(String org, Date startDate, Date endDate);

    /////////////////LOCATION
    //    //search status+location+date
    @Query("select distinct a " +
            "from Article a " +
            "join a.locationConnections aL " +
            "join aL.location aP " +
            "where (lower(aP.country) like lower(:location)) " +
            "and a.status in :status " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByLocationAndStatusAndDate(String location, List<Integer> status, Date startDate, Date endDate);


    //    //search in org, "contains" +date
    @Query("select distinct a " +
            "from Article a " +
            "join a.locationConnections aL " +
            "join aL.location aP " +
            "where (lower(aP.country) like lower(:location)) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByLocationAndDate(String location, Date startDate, Date endDate);


    ///////////////////////////////////////////////////////////////////////

    //search status+date
    @Query("from Article a " +
            "where a.status in (:status) and (a.date >=:startDate and a.date <= :endDate)")
    Set<Article> findByDateAndStatus(List<Integer> status, Date startDate, Date endDate);


    //search date range
    Set<Article> findAllByDateBetween(Date startDate, Date endDate);

    ///////////////////////////////////////////////////////////////////////

//
//    //search status+author
//    @Query("select distinct a " +
//            "from Article a " +
//            "join a.personConnections aL " +
//            "join aL.person aP " +
//            "where (lower(aP.surname) like lower(:author) " +
//            "or lower(aP.surnameRus) like lower(:author)) " +
//            "and a.status in :status ")
//    List<Article> findByAuthorAndStatus(String author, List<Integer> status);
//    @Query("select a " +
//            "from Article a " +
//            "join a.hashtagList h " +
//            "where lower(h.content) like lower(:hashTag) " +
//            "and (a.date >=:startDate and a.date <= :endDate)")
////            "where h.content = :hashTag")
//    List<Article> findByHashAndDate(String hashTag, Date startDate, Date endDate);
//
//    //search status+hash
//    @Query("select distinct a " +   //to remove duplicate if item has several hashtags start with search word
//            "from Article a " +
//            "join a.hashtagList h " +
//            "join h.assigned_hashtag assh " +
//            "where h.assigned_hashtag = assh.id " +
//            "and lower(assh.content) like lower(:hashTag)" +
//            "and a.status in (:status)")
//    List<Article> findByHashAndStatus(String hashTag, List<Integer> status);

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

//    //search status+title
//    @Query("from Article a " +
//            "where (lower(a.title) like lower(:title) or lower(a.titleRus) like lower(:title)) " +
//            "and a.status in (:status)")
//    List<Article> findByTitleAndStatus(@Param("title") String title, @Param("status") List<Integer> status);

//    //search status+description
//    @Query("from Article a " +
//            "where lower(a.description) like lower(:description) " +
//            "and a.status in (:status) ")
//    List<Article> findByDescriptionAndStatus(String description, List<Integer> status);

//
//    //search in language+status, "starts with"
//    @Query("from Article a " +
//            "where lower(a.language.name) like lower(:lang) " +
//            "and a.status in (:status) ")
//    List<Article> findByLangAndStatus(String lang, List<Integer> status);
//    //search status+miscellany
//    @Query("from Article a " +
//            "where lower(a.miscellany) like lower(:miscellany) " +
//            "and a.status in (:status) ")
//    List<Article> findByMiscellanyAndStatus(String miscellany, List<Integer> status);
//
//    //search status+org
//    @Query("select distinct a " +
//            "from Article a " +
//            "join a.orgConnections aL " +
//            "join aL.org aP " +
//            "where (lower(aP.name) like lower(:org) " +
//            "or lower(aP.nameRus) like lower(:org)) " +
//            "and a.status in :status ")
//    List<Article> findByOrgAndStatus(String org, List<Integer> status);
//
//    //search status+org
//    @Query("select distinct a " +
//            "from Article a " +
//            "join a.locationConnections aL " +
//            "join aL.location aP " +
//            "where (lower(aP.country) like lower(:location))" +
//            "and a.status in :status ")
//    List<Article> findByLocationAndStatus(String location, List<Integer> status);
//
//    //search by status
//    Set<Article> findAllByStatus(List<Integer> status);

}