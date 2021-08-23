package com.hellokoding.springboot.restful.service;
import com.hellokoding.springboot.restful.model.Status;

import java.util.List;
import java.util.Optional;

public interface StatusService {
    List<Status> findAll();
    Optional<Status> findById(Integer id);
    List<Status> search(String q);
}
