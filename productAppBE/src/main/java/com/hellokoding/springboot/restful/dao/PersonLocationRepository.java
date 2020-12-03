package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.PersonLocationConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonLocationRepository extends JpaRepository <PersonLocationConnection, Integer> {
}
