package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Org;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

public interface OrgRepository extends JpaRepository<Org, Integer> {
//public interface OrgRepository extends PagingAndSortingRepository<Org, Integer> {

    @Query("select org from " +
            "Org org " +
            "join org.movementList orgML " +
            "left join org.nameList nameL where nameL.priority = 1" +
            " and (orgML.id in :movement) group by org.id, nameL.name " +
            "order by nameL.name asc")
    Page<Org> findAllWithMovements(Pageable pageable, List<Integer> movement);
//    List<Org> findAllWithMovements(List<Integer> movement);



//    @Query(value = "SELECT u FROM User u ORDER BY id")
//    Page<Org> findAllUsersWithPagination(Pageable pageable);


///////////////////last working version
//    @Query("select org " +
//            "from Org org  " +
//            "join org.movementList orgML " +
//            "left join org.nameList nameL where nameL.priority = 1" +
//            " and (orgML.id in :movement) " +
//            "order by nameL.name asc")
//    List<Org> findAllWithMovements(List<Integer> movement);



    @Query("select distinct org " +
            "from Org org " +
            "join org.movementList oML " +
            "where (oML.id in :movement) ")
    List<Org> findAllWithMovement(List<Integer> movement);


// @Query("select distinct org " +
//            "from Org org " +
//            "join org.movementList oML " +
//            "where oML.id in (:movement) ")
//    List<Org> findAllWithMovements(List<Integer> movement);


//    @Query("select distinct o " +
//            "from Org o " +
//            "join o.movementList oML " +
//            "join o.nameList nmL " +
//            "where ((oML.id = :movement) " +
//            "and (lower(nmL.name)) like :name)")
//    List<Org> findByNameAndMovement(String name, Integer movement);

    @Query("select distinct org " +
            "from Org org " +
            "join org.movementList orgML " +
            "join org.nameList nmL " +
            "where ((lower(nmL.abbr) like :name) " +
            "or (lower(nmL.name)) like :name) " +
            "and orgML.id in :movement ")
    List<Org> findByName(String name, List<Integer> movement);


    @Query("select distinct o " +
            "from Org o " +
            "join o.nameList nmL " +
            "where ((lower(nmL.abbr) like :name) " +
            "or (lower(nmL.name)) like :name) ")
    List<Org> findByName(String name);  //works


//    @Query("select distinct org " +
//            "from Org as org " +
//            "left join org.nameList nameL " +
//            "where ((lower(nameL.name) like :name or lower(nameL.abbr) like :name) ) ")
//    Set<Org> findByName(String name);


//    @Query("select distinct o " +
//            "from Org o " +
//            "join o.movementList oML " +
//            "where (oML.id = :movement)")
//    List<Org> findByMovement(Integer movement);\\\\


    ///////////////FILTER/////////////////
    @Query("select distinct org " +
            "from Org as org " +
            "join org.movementList orgML " +
            "left join org.hashtagList orgH " +
            "left join orgH.assigned_hashtag assh " +
            "left join org.orgConnections orgC " +
            "left join orgC.org orgCOrg " +
            "left join orgCOrg.nameList nmL " +
            "left join org.locationConnections orgLC " +
            "left join orgLC.location orgLoc " +
            "left join org.nameList nameL " +
            "where ((:location is null or lower(orgLoc.country) like :location or lower(orgLoc.region) like :location or lower (orgLoc.city) like :location or lower(orgLoc.address) like :location or lower(orgLoc.placement) like :location) " +
            "and (:name is null or lower(nameL.name) like :name or lower(nameL.abbr) like :name) " +
            "and (:hashTag is null or lower(assh.content) like :hashTag)) " +
            "and (:orgConn is null  or lower(nmL.name) like :orgConn) " +
            "and orgML.id in :movement ")
    Set<Org> findByFilters(String hashTag, String name, String location, String orgConn, List<Integer> movement);

    @Query("select distinct org " +   //to remove duplicate if item has several hashtags start with search word
            "from Org org " +
            "join org.movementList orgML " +
            "join org.hashtagList orgH " +
            "join orgH.assigned_hashtag assh " +
            "where orgH.assigned_hashtag = assh.id " +
            "and lower(assh.content) like :hashTag " +
            "and orgML.id in :movement ")
    List<Org> findByHash(String hashTag, List<Integer> movement);

    @Query("select distinct org " +
            "from Org org " +
            "join org.movementList oML " +
            "join org.orgConnections orgOC " +
            "join orgOC.org orgOCOrg " +
            "join orgOCOrg.nameList nmL " +
            "where (lower(nmL.name) like :org) " +
            "and (oML.id in :movement) ")
    List<Org> findByOrg(String org, List<Integer> movement);

//
//    @Query("select distinct p " +
//            "from Person p " +
//            "join p.snpList snpL " +
//            "where (lower(snpL.surname) like lower(:surname)) ")
//    List<Person> findBySurname(String surname);

    @Query("select distinct org " +
            "from Org org " +
            "join org.movementList orgML " +
            "join org.locationConnections orgLC " +
            "join orgLC.location orgL " +
            "where (lower(orgL.country) like :location)" +
            "or (lower(orgL.city) like :location)" +
            "or (lower(orgL.region) like :location)" +
            "or (lower(orgL.address) like :location)" +
            "or (lower(orgL.placement) like :location) " +
            "and orgML.id in :movement ")
    List<Org> findByLocation(String location, List<Integer> movement);

}
