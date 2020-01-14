package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.ScpaperRepository;
import com.hellokoding.springboot.restful.model.Scpaper;
import com.hellokoding.springboot.restful.service.ScpaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScpaperServiceImpl implements ScpaperService {

    private final ScpaperRepository scpaperRepository;

    @Override
    public List<Scpaper> findAll() {
        List<Scpaper> all = scpaperRepository.findAll();
        return all;
    }

    @Override
    public Optional<Scpaper> findById(Integer id) {
        return scpaperRepository.findById(id);
    }

    @Override
    public Scpaper save(Scpaper stock) {
        return scpaperRepository.save(stock);
    }

    @Override
    public void deleteById(Integer id) {
        scpaperRepository.deleteById(id);
    }
}
