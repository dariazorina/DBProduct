package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.CountryRepository;
import com.hellokoding.springboot.restful.model.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service

@RequiredArgsConstructor
@Transactional
public class CountryService {
    private final CountryRepository countryRespository;

    public List<Country> findAll() {
        return countryRespository.findAll();
    }

    public Optional<Country> findById(Integer id) {
        return countryRespository.findById(id);
    }

    public Country save(Country country) {
        Country save = countryRespository.save(country);
        return save;
    }

    public void deleteById(Integer id) {
        countryRespository.deleteById(id);
    }
}
