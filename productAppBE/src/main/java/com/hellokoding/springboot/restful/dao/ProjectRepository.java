package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Org;
import com.hellokoding.springboot.restful.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query("select pro from " +
            "Project pro " +
            "join pro.movementList orgML " +
            "where (orgML.id in :movement) group by pro.id " +
            "order by pro.mainTitle asc")
    Page<Project> findAllWithMovements(Pageable pageable, List<Integer> movement);




//    @Query("select org from " +
//            "Org org " +
//            "join org.movementList orgML " +
//            "left join org.nameList nameL where nameL.priority = 1" +
//            " and (orgML.id in :movement) group by org.id, nameL.name " +
//            "order by nameL.name asc")
//    Page<Org> findAllWithMovements(Pageable pageable, List<Integer> movement);




    @Query("select distinct pro " +
            "from Project pro " +
            "join pro.movementList pML " +
            "where (pML.id in :movement) ")
    List<Project> findAllWithMovement(List<Integer> movement);



//    @Query("select distinct org " +
//            "from Org org " +
//            "join org.movementList orgML " +
//            "join org.nameList nmL " +
//            "where ((lower(nmL.abbr) like :name) " +
//            "or (lower(nmL.name)) like :name) " +
//            "and orgML.id in :movement ")
//    List<Org> findByName(String name, List<Integer> movement);
//
//
    @Query("select distinct pro " +
            "from Project pro " +
            "where ((lower(pro.mainTitle) like :name) " +
            "or (lower(pro.otherTitle)) like :name) ")
    List<Project> findByName(String name);  //works
//
//
//
//    ///////////////FILTER/////////////////
//    @Query("select distinct org " +
//            "from Org as org " +
//            "join org.movementList orgML " +
//            "left join org.hashtagList orgH " +
//            "left join orgH.assigned_hashtag assh " +
//            "left join org.orgConnections orgC " +
//            "left join orgC.org orgCOrg " +
//            "left join orgCOrg.nameList nmL " +
//            "left join org.locationConnections orgLC " +
//            "left join orgLC.location orgLoc " +
//            "left join org.nameList nameL " +
//            "where ((:location is null or lower(orgLoc.country) like :location or lower(orgLoc.region) like :location or lower (orgLoc.city) like :location or lower(orgLoc.address) like :location or lower(orgLoc.placement) like :location) " +
//            "and (:name is null or lower(nameL.name) like :name or lower(nameL.abbr) like :name) " +
//            "and (:hashTag is null or lower(assh.content) like :hashTag)) " +
//            "and (:orgConn is null  or lower(nmL.name) like :orgConn) " +
//            "and orgML.id in :movement ")
//    Set<Org> findByFilters(String hashTag, String name, String location, String orgConn, List<Integer> movement);
//
//    @Query("select distinct org " +   //to remove duplicate if item has several hashtags start with search word
//            "from Org org " +
//            "join org.movementList orgML " +
//            "join org.hashtagList orgH " +
//            "join orgH.assigned_hashtag assh " +
//            "where orgH.assigned_hashtag = assh.id " +
//            "and lower(assh.content) like :hashTag " +
//            "and orgML.id in :movement ")
//    List<Org> findByHash(String hashTag, List<Integer> movement);
//
//    @Query("select distinct org " +
//            "from Org org " +
//            "join org.movementList oML " +
//            "join org.orgConnections orgOC " +
//            "join orgOC.org orgOCOrg " +
//            "join orgOCOrg.nameList nmL " +
//            "where (lower(nmL.name) like :org) " +
//            "and (oML.id in :movement) ")
//    List<Org> findByOrg(String org, List<Integer> movement);
//
//
//    @Query("select distinct org " +
//            "from Org org " +
//            "join org.movementList orgML " +
//            "join org.locationConnections orgLC " +
//            "join orgLC.location orgL " +
//            "where (lower(orgL.country) like :location)" +
//            "or (lower(orgL.city) like :location)" +
//            "or (lower(orgL.region) like :location)" +
//            "or (lower(orgL.address) like :location)" +
//            "or (lower(orgL.placement) like :location) " +
//            "and orgML.id in :movement ")
//    List<Org> findByLocation(String location, List<Integer> movement);

}
