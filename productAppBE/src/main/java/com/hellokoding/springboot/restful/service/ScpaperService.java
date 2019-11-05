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

    public Optional<Scpaper> findById(Long id) {
        return scpaperRepository.findById(Math.toIntExact(id));
    }

    public Scpaper save(Scpaper stock) {
        return scpaperRepository.save(stock);
    }

    public void deleteById(Long id) {
        scpaperRepository.deleteById(Math.toIntExact(id));
    }
}
