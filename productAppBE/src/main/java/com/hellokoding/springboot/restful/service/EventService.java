package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.Event;
import com.hellokoding.springboot.restful.model.dto.*;

import java.util.List;
import java.util.Optional;

public interface EventService {
    //    List<Event> findAll();
//    Optional<Event> findById(Integer id);
    List<EventDtoForMainList> findAll(List<Integer> mov, Integer page, Integer size);

    List<OneTypeConnectionDto> findByIdsAndSymmetrically(Integer itemId);

    List<IdContentDto> search(String q);

    Event saveColor(EventDtoForMainList eventDto);

    EventDto findById(Integer id);

    Event save(EventDto stock);

    PagedDataDto filter(List<String> hash, List<String> name, List<String> location, List<String> org, List<Integer> mov, Integer page, Integer size);

    void deleteById(Integer id);
}
