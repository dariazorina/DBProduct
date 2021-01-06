package com.hellokoding.springboot.restful.service.impl;

import com.hellokoding.springboot.restful.dao.ConnectionTypeRepository;
import com.hellokoding.springboot.restful.model.ConnectionType;
import com.hellokoding.springboot.restful.service.ConnectionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ConnectionTypeServiceImpl implements ConnectionTypeService {
    private final ConnectionTypeRepository ctypeRepository;

    @Override
    public List<ConnectionType> findAll() {
        List<ConnectionType> all = ctypeRepository.findAll();
        return all;
    }

//    @Override
//    public Optional<ConnectionType> findById(Integer id) {
//        Optional<ConnectionType> byId = ctypeRepository.findById(id);
//        return byId;
//    }

    @Override
    public ConnectionType save(ConnectionType stock) {
        return ctypeRepository.save(stock);
    }

    @Override
    public void deleteById(Integer id) {
        ctypeRepository.deleteById(id);
    }

}
