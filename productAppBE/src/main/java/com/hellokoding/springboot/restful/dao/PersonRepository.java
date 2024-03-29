package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    //returns quantity with duplicates, wrong quantity for request ^^^
//    @Query("select p " +
//            "from Person p  " +
//            "join p.movementList pML " +
//            "where (pML.id in :movement) ")
//    List<Person> findAllWithMovement(List<Integer> movement);

//    @Query("select p " +
//            "from Person p  " +
//            "join p.movementList pML " +
//            "left join p.snpList snpL where snpL.priority = 1" +
//            " and (pML.id in :movement)  group by p.id, snpL.surname " +
//            "order by snpL.surname asc")
//    List<Person> findAllWithMovement(List<Integer> movement);

///*****************    last working version(with duplication)
//    @Query("select p " +
//            "from Person p  " +
//            "join p.movementList pML " +
//            "left join p.snpList snpL where snpL.priority = 1" +
//            " and (pML.id in :movement) " +
//            "order by snpL.surname asc")
//    List<Person> findAllWithMovement(List<Integer> movement);
    ////**************

//    @Query("select p " +
//            "from Person p  " +
//            "join p.movementList pML " +
//            "left join p.snpList snpL where snpL.priority = 1" +
//            " and (pML.id in :movement)  group by p.id, snpL.surname " +
//            "order by snpL.surname asc")
//    Page<Person> findAllWithMovements(Pageable pageable, List<Integer> movement);
//    List<Person> findAllWithMovement(List<Integer> movement);

    ///////////////FILTER/////////////////
    @Query("select p " +
            "from Person as p " +
            "left join p.movementList pML " +
            "left join p.hashtagList h " +
            "left join h.assigned_hashtag assh " +
            "left join p.orgConnections pO " +
            "left join pO.org pOrg " +
            "left join pOrg.nameList nmL " +
            "left join p.locationConnections pLo " +
            "left join pLo.location pLoc " +
            "left join p.snpList snpAllNames " +
            "left join p.snpList snpPrior where snpPrior.priority = 1" +
            "and ((:org is null  or lower(nmL.name) like :org) " +
            "and (:location is null or lower(pLoc.country) like :location or lower(pLoc.region) like :location or lower(pLoc.city) like :location or lower(pLoc.address) like :location or lower(pLoc.placement) like :location) " +
            "and (:surname is null or lower(snpAllNames.surname) like :surname or lower(snpAllNames.name) like :surname) " +
            "and (:hashTag is null or assh.content like :hashTag)) " +
            "and (pML.id in :movement) group by p.id, snpPrior.surname " +
            "order by snpPrior.surname asc")
    Page<Person> findByFilters(Pageable pageable, String hashTag, String surname, String org, String location, List<Integer> movement);

    @Query("select p " +   //to remove duplicate if item has several hashtags start with search word
            "from Person p " +
            "join p.movementList pML " +
            "join p.hashtagList h " +
            "join h.assigned_hashtag assh " +
            "join p.snpList snpL where snpL.priority = 1 " +
            "and h.assigned_hashtag = assh.id " +
            "and lower(assh.content) in :hashTag " +
            "and pML.id in :movement group by p.id, snpL.surname order by snpL.surname desc")
    Page<Person> findByHash(Pageable pageable, List<String> hashTag, List<Integer> movement);

//    @Query("select distinct p " +
//            "from Person p " +
//            "join p.personConnections aL " +
//            "join aL.person aP " +
//            "where (lower(aP.surname) like lower(:surname) " +
//            "or lower(aP.surnameRus) like lower(:surname) ) ")
//    List<Person> findBySurname(String surname);
//


    //there is no sense to order result by surname if search works for surname with prior = 0 too
    @Query("select p " +
            "from Person p " +
            "join p.snpList snpAllName " +
            "join p.movementList pML " +
            "join p.snpList snpPrior where snpPrior.priority = 1 " +
            "and ((lower(snpAllName.name) like :surname)" +
            "or (lower(snpAllName.surname) like :surname)) " +
            "and (pML.id in :movement) group by p.id, snpPrior.surname order by snpPrior.surname asc")
    Page<Person> findBySurname(Pageable pageable, String surname, List<Integer> movement);
    //List<Person> findBySurname(String surname, List<Integer> movement);


    @Query("select distinct p " +
            "from Person p " +
            "join p.snpList snpL " +
            "where ((lower(snpL.name) like :surname)" +
            "or (lower(snpL.surname) like :surname)) ")
    List<Person> findBySurname(String surname);


    @Query("select distinct p " +
            "from Person p " +
            "join p.movementList pML " +
            "join p.orgConnections aL " +
            "join aL.org aP " +
            "join aP.nameList nmL " +
            "where (lower(nmL.name) like :org) " +
            "and (pML.id in :movement) ")
    Page<Person> findByOrg(Pageable paging, String org, List<Integer> movement);
//    List<Person> findByOrg(String org, List<Integer> movement);


    @Query("select p " +
            "from Person p " +
            "join p.snpList snpL " +
            "join p.movementList pML " +
            "join p.locationConnections aLC " +
            "join aLC.location aLoc " +
            "where (lower(aLoc.country) like :location)" +
            "or (lower(aLoc.city) like :location)" +
            "or (lower(aLoc.region) like :location)" +
            "or (lower(aLoc.address) like :location)" +
            "or (lower(aLoc.placement) like :location) " +
            "and (pML.id in :movement) group by p.id, aLoc.country, aLoc.city, aLoc.region, aLoc.address, aLoc.placement, snpL.surname order by snpL.surname asc")
    Page<Person> findByLocation(Pageable pageable, String location, List<Integer> movement);
//    List<Person> findByLocation(String location, List<Integer> movement);

}


//    @Query("select p " +
//            "from Person p  " +
//            "join p.movementList pML " +
//            "left join p.snpList snpL " +
//            "where (pML.id in :movement) and " +
//            "snpL.priority = 1 " +
//            "order by snpL.surname asc")
//    List<Person> findAllWithMovement(List<Integer> movement);

//    select *
//    from Person p
//    join PersonMovements pm on p.person_id=pm.person_id
//    left join PersonSurnames ps on p.person_id=ps.person_id and ps.priority=1
//    where pm.id in (...)
//    order by ps.surname


