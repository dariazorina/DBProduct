package com.hellokoding.springboot.restful.dao;

import com.hellokoding.springboot.restful.model.ConnectionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConnectionTypeRepository extends JpaRepository <ConnectionType, Integer> {
    Optional<ConnectionType> findByType(String type);
    Optional<ConnectionType> findById(Integer id);
}
