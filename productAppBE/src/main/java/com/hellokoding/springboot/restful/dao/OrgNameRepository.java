package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.OrgName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgNameRepository extends JpaRepository<OrgName, Integer> {
}
