package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.HashTagRepository;
import com.hellokoding.springboot.restful.model.HashTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashTagService {

    private final HashTagRepository authorRepository;

    public List<HashTag> findAll() {
        return authorRepository.findAll();
    }

    public Optional<HashTag> findById(Long id) {
        return authorRepository.findById(Math.toIntExact(id));
    }

    public HashTag save(HashTag stock) {
        return authorRepository.save(stock);
    }

    public void deleteById(Long id) {
        //productRespository.deleteById(id);
    }

}
