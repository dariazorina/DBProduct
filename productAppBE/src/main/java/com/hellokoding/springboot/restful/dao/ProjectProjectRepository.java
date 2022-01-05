package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.OrgOrgConnection;
import com.hellokoding.springboot.restful.model.ProjectProjectConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectProjectRepository extends JpaRepository <ProjectProjectConnection, Integer> {

    @Query("select distinct pro " +
            "from ProjectProjectConnection pro " +
            "left join pro.connectedProject proC " +
            "where (proC.id = :id)")
    List<ProjectProjectConnection> findByIdSymm(Integer id);


    @Query("select distinct pro " +
            "from ProjectProjectConnection pro " +
            "left join pro.project proP " +
            "where (proP.id = :id)")
    List<ProjectProjectConnection> findByIdSimple(Integer id);


}
