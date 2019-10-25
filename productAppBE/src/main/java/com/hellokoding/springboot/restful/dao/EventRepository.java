package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
