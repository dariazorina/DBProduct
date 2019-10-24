package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.UrlLinkRepository;
import com.hellokoding.springboot.restful.model.UrlLink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlLinkService {

    private final UrlLinkRepository urlLinkRepository;

    public List<UrlLink> findAll() {
        return urlLinkRepository.findAll();
    }

    public Optional<UrlLink> findById(Long id) {
        return urlLinkRepository.findById(Math.toIntExact(id));
    }

    public UrlLink save(UrlLink stock) {
        return urlLinkRepository.save(stock);
    }

    public void deleteById(Long id) {
        urlLinkRepository.deleteById(Math.toIntExact(id));
    }
}
