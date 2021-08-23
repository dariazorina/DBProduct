package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.hellokoding.springboot.restful.model.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    @Override
    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    @Override
    public Optional<Status> findById(Integer id) {
        return statusRepository.findById(id);
    }

    @Override
    public List<Status> search(String q) {
        return statusRepository.findByNameEqualsOrderByName(q);
    }
}
