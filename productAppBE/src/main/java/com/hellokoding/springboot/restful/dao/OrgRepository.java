package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Org;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrgRepository extends JpaRepository <Org, Integer> {

    @Query("select org from " +
            "Org org " +
            "join org.movementList orgML " +
            "left join org.nameList nameL where nameL.priority = 1" +
            " and (orgML.id in :movement) group by org.id, nameL.name " +
            "order by nameL.name asc")
    List<Org> findAllWithMovements(List<Integer> movement);

///////////////////last working version
//    @Query("select org " +
//            "from Org org  " +
//            "join org.movementList orgML " +
//            "left join org.nameList nameL where nameL.priority = 1" +
//            " and (orgML.id in :movement) " +
//            "order by nameL.name asc")
//    List<Org> findAllWithMovements(List<Integer> movement);




//    @Query("select distinct org " +
//            "from Org org " +
//            "join org.movementList oML " +
//            "where (oML.id = :movement) ")
//    List<Org> findAllWithMovement(Integer movement);


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

  @Query("select distinct o " +
            "from Org o " +
            "join o.nameList nmL " +
            "where ((lower(nmL.abbr) like :name) " +
            "or (lower(nmL.name)) like :name)")
    List<Org> findByName(String name);  //works

//    @Query("select distinct o " +
//            "from Org o " +
//            "join o.movementList oML " +
//            "where (oML.id = :movement)")
//    List<Org> findByMovement(Integer movement);

}
