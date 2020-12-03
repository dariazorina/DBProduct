package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.controller.UniqueConstraintViolationException;
import com.hellokoding.springboot.restful.dao.LocationRepository;
import com.hellokoding.springboot.restful.model.Location;
import com.hellokoding.springboot.restful.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> findById(Integer id) {
        return locationRepository.findById(id);
    }

    @Override
    @Transactional(noRollbackFor = UniqueConstraintViolationException.class)
    public Location save(Location location) throws UniqueConstraintViolationException {
        try {
            return locationRepository.save(location);
        } catch (DataIntegrityViolationException ex) {
            throw new UniqueConstraintViolationException();
        }
    }

    @Override
    public void deleteById(Integer id) {
        locationRepository.deleteById(id);
    }
}
