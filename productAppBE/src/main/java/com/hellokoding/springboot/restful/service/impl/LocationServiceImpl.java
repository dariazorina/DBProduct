package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.controller.UniqueConstraintViolationException;
import com.hellokoding.springboot.restful.dao.LocationRepository;
import com.hellokoding.springboot.restful.model.Location;
import com.hellokoding.springboot.restful.model.Person;
import com.hellokoding.springboot.restful.model.dto.IdContentDto;
//import com.hellokoding.springboot.restful.model.dto.LocationDto;
//import com.hellokoding.springboot.restful.model.dto.PersonDto;
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
    public List<IdContentDto> search(String q) {

        List<Location> countrySearchList = locationRepository.findLocation("%" + q + "%");
        return transformOriginToDto(countrySearchList);
    }

    public List<IdContentDto> findByIds(List<Integer> idList) {

        List<Location> searchRes = new ArrayList<>();
        for (Integer id : idList) {
            Optional<Location> l = locationRepository.findById(id);

            if ( l != null ) {
                searchRes.add(l.get());
            }
        }
        return transformOriginToDto(searchRes);
    }

    public List<IdContentDto> transformOriginToDto(List<Location> locationList) {
        Set<IdContentDto> fooSet = new TreeSet<>();
        String dtoName = "";

        for (Location location : locationList) {
            dtoName = location.getCountry();
            if (location.getRegion() != null) {
                dtoName += ", " + location.getRegion();
            }
            if (location.getCity() != null) {
                dtoName += ", " + location.getCity();
            }
            if (location.getAddress() != null) {
                dtoName += ", " + location.getAddress();
            }
            if (location.getPlacement() != null) {
                dtoName += ", " + location.getPlacement();
            }
            IdContentDto locationDto = new IdContentDto(location.getId(), dtoName);
            dtoName = "";
            fooSet.add(locationDto);
        }
        List<IdContentDto> finalList = new ArrayList<>(fooSet);
        return finalList;
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
