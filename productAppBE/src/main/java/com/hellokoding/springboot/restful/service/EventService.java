package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> findAll();

    Optional<Event> findById(Integer id);

    Event save(Event stock);

    void deleteById(Integer id);
}
