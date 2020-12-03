package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository <Location, Integer>{
}
