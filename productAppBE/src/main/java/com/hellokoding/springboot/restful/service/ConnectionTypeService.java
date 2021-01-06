package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.model.ConnectionType;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface ConnectionTypeService {
    List<ConnectionType> findAll();
//    Optional<ConnectionType> findById(Integer id);
    ConnectionType save(ConnectionType stock);
    void deleteById(Integer id);
}
