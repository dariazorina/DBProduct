package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.ScpaperRepository;
import com.hellokoding.springboot.restful.model.Scpaper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScpaperService {

    private final ScpaperRepository scpaperRepository;

    public List<Scpaper> findAll() {
        List<Scpaper> all = scpaperRepository.findAll();
        return all;
    }

    public Optional<Scpaper> findById(Integer id) {
        return scpaperRepository.findById(id);
    }

    public Scpaper save(Scpaper stock) {
        return scpaperRepository.save(stock);
    }

    public void deleteById(Integer id) {
        scpaperRepository.deleteById(id);
    }
}
