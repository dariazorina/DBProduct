package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.IsourceRepository;
import com.hellokoding.springboot.restful.model.Isource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class IsourceService {

    private final IsourceRepository isourceRepository;

    public List<Isource> findAll() {
        List<Isource> all = isourceRepository.findAll();
        return all;
    }

    public Optional<Isource> findById(Long id) {
        return isourceRepository.findById(Math.toIntExact(id));
    }

    public Isource save(Isource stock) {
        return isourceRepository.save(stock);
    }

    public void deleteById(Long id) {
        isourceRepository.deleteById(Math.toIntExact(id));
    }
}
