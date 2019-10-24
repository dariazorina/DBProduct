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

    private final HashTagRepository hashTagRepository;

    public List<HashTag> findAll() {
        return hashTagRepository.findAll();
    }

    public Optional<HashTag> findById(Long id) {
        return hashTagRepository.findById(Math.toIntExact(id));
    }

    public HashTag save(HashTag stock) {
        return hashTagRepository.save(stock);
    }

    public void deleteById(Long id) {
        //productRespository.deleteById(id);
    }

}
