package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("select event from " +
            "Event event " +
            "join event.movementList eventML " +
            "where (eventML.id in :movement) group by event.id " +
            "order by event.title asc")
    Page<Event> findAllWithMovements(Pageable pageable, List<Integer> movement);

    @Query("select distinct event " +
            "from Event event " +
            "where (lower(event.title) like :name)")
    List<Event> findByName(String name);  //works

//    @Query("select pro from " +
//            "Project pro " +
//            "join pro.movementList orgML " +
//            "where (orgML.id in :movement) group by pro.id " +
//            "order by pro.mainTitle asc")
//    List<Event> findAllWithMovement(List<Integer> movement);

}
