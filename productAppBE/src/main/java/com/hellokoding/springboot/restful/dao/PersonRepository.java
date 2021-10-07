package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("select distinct p " +
            "from Person p " +
            "join p.movementList pML " +
            "where (pML.id = :movement) ")
    List<Person> findAllWithMovement(Integer movement);

    //Person getPersonByName(String name);

    //List<Person> findBySurnameStartsWithIgnoreCase(String surname);
    @Query("select distinct p " +
            "from Person p " +
            "join p.movementList pML " +
            "join p.snpList snpL " +
            "where (pML.id = :movement) " +
            "and lower(snpL.surname) like :surname")
    List<Person> findBySurnameAndMovement(String surname, Integer movement);

    ///////////////FILTER/////////////////
    @Query("select distinct a " +
            "from Person as a " +
            "left join a.hashtagList h " +
            "left join h.assigned_hashtag assh " +
            "left join a.orgConnections aO " +
            "left join aO.org aOrg " +
            "left join aOrg.nameList nmL " +
            "left join a.locationConnections aLo " +
            "left join aLo.location aLoc " +
            "left join a.snpList snpL " +
            "where ((:org is null  or lower(nmL.name) like :org) " +
            "and (:location is null or lower(aLoc.country) like :location) " +
            "and (:surname is null or lower(snpL.surname) like :surname) " +
            "and (:hashTag is null or assh.content like :hashTag)) ")
    Set<Person> findByFilters(String hashTag, String surname, String org, String location);

    @Query("select distinct p " +   //to remove duplicate if item has several hashtags start with search word
            "from Person p " +
            "join p.hashtagList h " +
            "join h.assigned_hashtag assh " +
            "where h.assigned_hashtag = assh.id " +
            "and lower(assh.content) like lower(:hashTag)")
    List<Person> findByHash(String hashTag);

//    @Query("select distinct p " +
//            "from Person p " +
//            "join p.personConnections aL " +
//            "join aL.person aP " +
//            "where (lower(aP.surname) like lower(:surname) " +
//            "or lower(aP.surnameRus) like lower(:surname) ) ")
//    List<Person> findBySurname(String surname);
//

    @Query("select distinct p " +
            "from Person p " +
            "join p.snpList snpL " +
            "where (lower(snpL.surname) like lower(:surname)) ")
    List<Person> findBySurname(String surname);

    @Query("select distinct p " +
            "from Person p " +
            "join p.orgConnections aL " +
            "join aL.org aP " +
            "join aP.nameList nmL " +
            "where (lower(nmL.name) like lower(:org)) ")
    List<Person> findByOrg(String org);

    @Query("select distinct p " +
            "from Person p " +
            "join p.locationConnections aL " +
            "join aL.location aP " +
            "where (lower(aP.country) like lower(:location)) ")
    List<Person> findByLocation(String location);

}