package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.controller.UniqueConstraintViolationException;
import com.hellokoding.springboot.restful.dao.IsourceRepository;
import com.hellokoding.springboot.restful.dao.MovementRepository;
import com.hellokoding.springboot.restful.dao.OrgRepository;
import com.hellokoding.springboot.restful.model.Movement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.UniqueConstraint;
import java.util.List;
import java.util.Optional;

public interface MovementService {


    public List<Movement> findAll();

    public Optional<Movement> findById(Integer id);

    public Movement save(Movement stock) throws UniqueConstraintViolationException;

    public void deleteById(Integer id);

}
