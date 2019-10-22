package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository <Country, Integer>{
}
