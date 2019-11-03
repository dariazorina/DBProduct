package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Isource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IsourceRepository extends JpaRepository<Isource, Integer> {
}
