package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.IsourceRepository;
import com.hellokoding.springboot.restful.model.Isource;
import com.hellokoding.springboot.restful.service.IsourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class IsourceServiceImpl implements IsourceService {

    private final IsourceRepository isourceRepository;

    @Override
    public List<Isource> findAll() {
        List<Isource> all = isourceRepository.findAll();
        return all;
    }

    @Override
    public Optional<Isource> findById(Integer id) {
        return isourceRepository.findById(id);
    }

    @Override
    public Isource save(Isource stock) {
        return isourceRepository.save(stock);
    }

    @Override
    public void deleteById(Integer id) {
        isourceRepository.deleteById(id);
    }
}
