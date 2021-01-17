package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    @Query("SELECT location FROM Location location " +
            "where (lower(location.country) like lower(:q) " +
            "or lower(location.region) like lower(:q) " +
            "or lower(location.city) like lower(:q) " +
            "or lower(location.address) like lower(:q) " +
            "or lower(location.placement) like lower(:q))")
    List<Location> findLocation(String q);
}
