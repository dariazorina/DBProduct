package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.controller.UniqueConstraintViolationException;
import com.hellokoding.springboot.restful.dao.LocationRepository;
import com.hellokoding.springboot.restful.model.Location;
import com.hellokoding.springboot.restful.model.Person;
import com.hellokoding.springboot.restful.model.dto.LocationDto;
import com.hellokoding.springboot.restful.model.dto.PersonDto;
import com.hellokoding.springboot.restful.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Override
    public List<LocationDto> search(String q) {

        List<Location> countrySearchList = locationRepository.findByCountryStartsWithIgnoreCase(q);
//        List<Person> surnameRusSearchList = locationRepository.findBySurnameRusStartsWithIgnoreCase(q);
//        List<Person> surnameEngSearchList = locationRepository.findBySurnameEngStartsWithIgnoreCase(q);

        Set<LocationDto> fooSet = new TreeSet<>();
        String dtoName;

        for (Location location : countrySearchList) {
            dtoName = location.getCountry();
            if (location.getRegion()!= null) {
                dtoName += ", " + location.getRegion();
            }
            if (location.getCity() != null) {
                dtoName += ", " + location.getCity();
            }
            if (location.getAddress() != null) {
                dtoName += ", " + location.getAddress();
            }
            if (location.getPlacement()!= null) {
                dtoName += ", " + location.getPlacement();
            }
            LocationDto locationDto = new LocationDto(location.getId(), dtoName);
            fooSet.add(locationDto);
        }
        List<LocationDto> finalList = new ArrayList<>(fooSet);
        return finalList;
    }

    public List<Location> findByIds(List<Integer> idList) {

        List<Location> searchRes = new ArrayList<>();
        for (Integer id : idList) {
            Optional<Location> l = locationRepository.findById(id);

            if ( l != null ) {
                searchRes.add(l.get());
            }
        }
        return searchRes;
    }

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
