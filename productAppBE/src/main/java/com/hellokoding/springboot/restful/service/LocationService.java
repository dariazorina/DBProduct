package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.controller.UniqueConstraintViolationException;
import com.hellokoding.springboot.restful.model.Location;
import com.hellokoding.springboot.restful.model.dto.LocationDto;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<LocationDto> search(String q);
    List<Location> findAll();
    List<Location> findByIds(List<Integer> idList);
    Optional<Location> findById(Integer id);
    Location save(Location location) throws UniqueConstraintViolationException;
    void deleteById(Integer id);
}
