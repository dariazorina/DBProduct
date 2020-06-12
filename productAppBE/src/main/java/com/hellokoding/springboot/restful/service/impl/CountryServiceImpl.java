package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.controller.UniqueConstraintViolationException;
import com.hellokoding.springboot.restful.dao.CountryRepository;
import com.hellokoding.springboot.restful.model.Country;
import com.hellokoding.springboot.restful.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRespository;

    @Override
    public List<Country> findAll() {
        return countryRespository.findAll();
    }

    @Override
    public Optional<Country> findById(Integer id) {
        return countryRespository.findById(id);
    }

    @Override
    @Transactional(noRollbackFor = UniqueConstraintViolationException.class)
    public Country save(Country country) throws UniqueConstraintViolationException {
        try {
            return countryRespository.save(country);
        } catch (DataIntegrityViolationException ex) {
            throw new UniqueConstraintViolationException();
        }
    }

    @Override
    public void deleteById(Integer id) {
        countryRespository.deleteById(id);
    }
}
