package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.EventRepository;
import com.hellokoding.springboot.restful.model.Article;
import com.hellokoding.springboot.restful.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class EventService {
    private final EventRepository eventRepository;

    public List<Event> findAll() {
        List<Event> all = eventRepository.findAll();
        return all;
    }

    public Optional<Event> findById(Long id) {
        Optional<Event> byId = eventRepository.findById(Math.toIntExact(id));
        return byId;
    }

    public Event save(Event stock) {
        return eventRepository.save(stock);
    }

    public void deleteById(Long id) {
        eventRepository.deleteById(Math.toIntExact(id));
    }
}
