package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    Optional<Status> getByName(String name);
    List<Status> findByNameEqualsOrderByName(String name);
}
