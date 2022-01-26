package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.EventEventConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventEventRepository extends JpaRepository <EventEventConnection, Integer> {

    @Query("select distinct event " +
            "from EventEventConnection event " +
            "left join event.connectedEvent eventC " +
            "where (eventC.id = :id)")
    List<EventEventConnection> findByIdSymm(Integer id);


    @Query("select distinct event " +
            "from EventEventConnection event " +
            "left join event.event eventP " +
            "where (eventP.id = :id)")
    List<EventEventConnection> findByIdSimple(Integer id);


}
