package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.controller.UniqueConstraintViolationException;
import com.hellokoding.springboot.restful.model.Country;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    List<Country> findAll();

    Optional<Country> findById(Integer id);

    Country save(Country country) throws UniqueConstraintViolationException;

    void deleteById(Integer id);
}
