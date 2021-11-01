package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.PersonPersonConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonPersonRepository extends JpaRepository <PersonPersonConnection, Integer> {

    @Query("select distinct p " +
            "from PersonPersonConnection p " +
            "left join p.connectedPerson pC " +
            "where (pC.id = :id)")
    List<PersonPersonConnection> findByIdSymm(Integer id);

    @Query("select distinct p " +
            "from PersonPersonConnection p " +
            "left join p.person pC " +
            "where (pC.id = :id)")
    List<PersonPersonConnection> findByIdSimple(Integer id);

}
