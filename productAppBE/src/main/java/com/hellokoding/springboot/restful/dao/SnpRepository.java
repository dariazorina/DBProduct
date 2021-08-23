package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.SurnameNamePatr;
import com.hellokoding.springboot.restful.model.UrlLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnpRepository extends JpaRepository<SurnameNamePatr, Integer> {
}
