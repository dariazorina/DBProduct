package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.controller.UniqueConstraintViolationException;
import com.hellokoding.springboot.restful.model.Country;
import com.hellokoding.springboot.restful.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<Location> findAll();

    Optional<Location> findById(Integer id);

    Location save(Location location) throws UniqueConstraintViolationException;

    void deleteById(Integer id);
}
