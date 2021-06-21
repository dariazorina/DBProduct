package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Org;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrgRepository extends JpaRepository <Org, Integer> {
    Org getOrgByNameRus(String name);
    List<Org> findByNameStartsWithIgnoreCase(String q);
    List<Org> findByNameRusStartsWithIgnoreCase(String q);

//    @Query("from Org as org where lower(org.name) like lower(:name)")
//    List<Org> findByOrgName(String name);

    @Query("from Org as org " +
            "join org.movementList oML " +
            "where (lower(org.name) like lower(:name) " +
            "or lower(org.nameRus) like lower(:name) " +
            "or lower(org.nameEng) like lower(:name) " +
            "or lower(org.abbr) like lower(:name) " +
            "or lower(org.abbrRus) like lower(:name) " +
            "or lower(org.abbrEng) like lower(:name))" +
            "and oML.id = :movement")
    List<Org> findByOrgName(String name, Integer movement);
}
