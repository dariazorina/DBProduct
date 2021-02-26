package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    @Query("from Article as a " +
            "where (lower(a.title) like lower(:title) " +
            "or lower(a.titleRus) like lower(:title))")
    List<Article> findMaterialByTitle(String title);
//    List<Article> findByTitleStartsWithIgnoreCase(String title);

    //search in title (title, titleRus simultaneously), "contains" +date
    @Query("from Article as a " +
            "where (lower(a.title) like lower(:title) " +
            "or lower(a.titleRus) like lower(:title)) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByTitleAndDate(String title, Date startDate, Date endDate);
//    List<Article> findByTitleAndDate(List<String> title, Date startDate, Date endDate);

    //search in description, "contains" +date
    @Query("from Article as a " +
            "where lower(a.description) like lower(:description) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByDescriptionAndDate(String description, Date startDate, Date endDate);

    //search in language, "starts with"
    @Query("select a from Article as a join a.language l where lower(l.name) like lower(:lang) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByLangAndDate(String lang, Date startDate, Date endDate);

    //search in hash, "starts with" +date
    @Query("select distinct a " +   //to remove duplicate if item has several hashtags start with search word
            "from Article a " +
            "join a.hashtagList h " +
            "join h.assigned_hashtag assh " +
            "where h.assigned_hashtag = assh.id " +
            "and lower(assh.content) like lower(:hashTag)" +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByHashAndDate(String hashTag, Date startDate, Date endDate);

//    @Query("select a " +
//            "from Article a " +
//            "join a.hashtagList h " +
//            "where lower(h.content) like lower(:hashTag) " +
//            "and (a.date >=:startDate and a.date <= :endDate)")
////            "where h.content = :hashTag")
//    List<Article> findByHashAndDate(String hashTag, Date startDate, Date endDate);


    //search in author surname and surnameRus, "starts with" +date
    @Query("select distinct a " +
            "from Article a " +
            "join a.personConnections aL " +
            "join aL.person aP " +
            "where (lower(aP.surname) like lower(:author) " +
            "or lower(aP.surnameRus) like lower(:author) ) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByAuthorAndDate(String author, Date startDate, Date endDate);

    ///////////////////////////////////////////////////////////////////


    //search status+date
    @Query("from Article a " +
            "where a.status in (:status) and (a.date >=:startDate and a.date <= :endDate)")
    Set<Article> findByDateAndStatus(List<Integer> status, Date startDate, Date endDate);


    //////////////////////////////////////////////////////////////////////

    //search status+author+date
    @Query("select distinct a " +
            "from Article a " +
            "join a.personConnections aL " +
            "join aL.person aP " +
            "where (lower(aP.surname) like lower(:author) " +
            "or lower(aP.surnameRus) like lower(:author)) " +
            "and a.status in :status " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByAuthorAndStatusAndDate(String author, List<Integer> status, Date startDate, Date endDate);

    //search status+author
    @Query("select distinct a " +
            "from Article a " +
            "join a.personConnections aL " +
            "join aL.person aP " +
            "where (lower(aP.surname) like lower(:author) " +
            "or lower(aP.surnameRus) like lower(:author)) " +
            "and a.status in :status ")
    List<Article> findByAuthorAndStatus(String author, List<Integer> status);


//    @Query("select a " +
//            "from Article a " +
//            "join a.hashtagList h " +
//            "where lower(h.content) like lower(:hashTag) " +
//            "and (a.date >=:startDate and a.date <= :endDate)")
////            "where h.content = :hashTag")
//    List<Article> findByHashAndDate(String hashTag, Date startDate, Date endDate);


    //search status+hash+date
    @Query("select distinct a " +   //to remove duplicate if item has several hashtags start with search word
            "from Article a " +
            "join a.hashtagList h " +
            "join h.assigned_hashtag assh " +
            "where h.assigned_hashtag = assh.id " +
            "and lower(assh.content) like lower(:hashTag)" +
            "and a.status in (:status) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByHashAndStatusAndDate(String hashTag, List<Integer> status, Date startDate, Date endDate);

    //search status+hash
    @Query("select distinct a " +   //to remove duplicate if item has several hashtags start with search word
            "from Article a " +
            "join a.hashtagList h " +
            "join h.assigned_hashtag assh " +
            "where h.assigned_hashtag = assh.id " +
            "and lower(assh.content) like lower(:hashTag)" +
            "and a.status in (:status)")
    List<Article> findByHashAndStatus(String hashTag, List<Integer> status);


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


    //search status+title+date
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


    //search status+description+date
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


    //search status+misc+date
    @Query("from Article a " +
            "where lower(a.miscellany) like lower(:miscellany) " +
            "and a.status in (:status) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByMiscellanyAndStatusAndDate(String miscellany, List<Integer> status, Date startDate, Date endDate);

    //search status+miscellany
    @Query("from Article a " +
            "where lower(a.miscellany) like lower(:miscellany) " +
            "and a.status in (:status) ")
    List<Article> findByMiscellanyAndStatus(String miscellany, List<Integer> status);

    //search in miscellany, "contains" +date
    @Query("from Article as a " +
            "where lower(a.miscellany) like lower(:miscellany) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByMiscellanyAndDate(String miscellany, Date startDate, Date endDate);

    //search status+org+date
    @Query("select distinct a " +
            "from Article a " +
            "join a.orgConnections aL " +
            "join aL.org aP " +
            "where (lower(aP.name) like lower(:org) " +
            "or lower(aP.nameRus) like lower(:org)) " +
            "and a.status in :status " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByOrgAndStatusAndDate(String org, List<Integer> status, Date startDate, Date endDate);

    //search status+org
    @Query("select distinct a " +
            "from Article a " +
            "join a.orgConnections aL " +
            "join aL.org aP " +
            "where (lower(aP.name) like lower(:org) " +
            "or lower(aP.nameRus) like lower(:org)) " +
            "and a.status in :status ")
    List<Article> findByOrgAndStatus(String org, List<Integer> status);

    //search in org, "contains" +date
    @Query("select distinct a " +
            "from Article a " +
            "join a.orgConnections aL " +
            "join aL.org aP " +
            "where (lower(aP.name) like lower(:org) " +
            "or lower(aP.nameRus) like lower(:org) ) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByOrgAndDate(String org, Date startDate, Date endDate);

    //search status+location+date
    @Query("select distinct a " +
            "from Article a " +
            "join a.locationConnections aL " +
            "join aL.location aP " +
            "where (lower(aP.country) like lower(:location)) " +
            "and a.status in :status " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByLocationAndStatusAndDate(String location, List<Integer> status, Date startDate, Date endDate);

    //search status+org
    @Query("select distinct a " +
            "from Article a " +
            "join a.locationConnections aL " +
            "join aL.location aP " +
            "where (lower(aP.country) like lower(:location))" +
            "and a.status in :status ")
    List<Article> findByLocationAndStatus(String location, List<Integer> status);

    //search in org, "contains" +date
    @Query("select distinct a " +
            "from Article a " +
            "join a.locationConnections aL " +
            "join aL.location aP " +
            "where (lower(aP.country) like lower(:location)) " +
            "and (a.date >=:startDate and a.date <= :endDate)")
    List<Article> findByLocationAndDate(String location, Date startDate, Date endDate);


    //search date range
    Set<Article> findAllByDateBetween(Date startDate, Date endDate);

    //search by status
    Set<Article> findAllByStatus(List<Integer> status);

}