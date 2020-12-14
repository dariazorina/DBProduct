package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository <Location, Integer>{
    List<Location> findByCountryStartsWithIgnoreCase(String q);
}
