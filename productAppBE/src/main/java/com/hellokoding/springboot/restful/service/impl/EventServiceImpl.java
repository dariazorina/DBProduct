package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.EventRepository;
import com.hellokoding.springboot.restful.model.Event;
import com.hellokoding.springboot.restful.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public List<Event> findAll() {
        List<Event> all = eventRepository.findAll();
        return all;
    }

    @Override
    public Optional<Event> findById(Integer id) {
        Optional<Event> byId = eventRepository.findById(id);
        return byId;
    }

    @Override
    public Event save(Event stock) {
        return eventRepository.save(stock);
    }

    @Override
    public void deleteById(Integer id) {
        eventRepository.deleteById(id);
    }
}
