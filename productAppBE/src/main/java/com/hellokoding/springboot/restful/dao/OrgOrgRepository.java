package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.OrgOrgConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrgOrgRepository extends JpaRepository <OrgOrgConnection, Integer> {

    @Query("select distinct o " +
            "from OrgOrgConnection o " +
            "left join o.connectedOrg oC " +
            "where (oC.id = :id)")
    List<OrgOrgConnection> findByIdSymm(Integer id);


    @Query("select distinct org " +
            "from OrgOrgConnection org " +
            "left join org.org orgO " +
            "where (orgO.id = :id)")
    List<OrgOrgConnection> findByIdSimple(Integer id);


}
